export const client = {
    current: null,
    // 发送广播
    sendBroadcast: text => {
        client.current.send("/app/broadcast", {}, JSON.stringify({ text }));
    },
    // 发送私聊
    sendTo: (listener, text) => {
        client.current.send("/app/chat", {}, JSON.stringify({ listener, text }));
    },
    // 断开链接
    disconnect: () => {
        client.current && client.current.disconnect();
        client.current = null;
    },
    // 建立链接
    connect: (username, funcs) => {
        const { updateMembers, subscribeBroadcast, subscribeMessages, whenConnect, whenError } = funcs;
        client.disconnect();
        // http://localhost:8081
        client.current = Stomp.over(new SockJS('/chat-server'));
        client.current.connect({ username }, () => {
            whenConnect();

            // 更新成员列表
            client.current.subscribe('/topic/members', output => {
                updateMembers(JSON.parse(output.body));
            });

            // 接受广播消息
            client.current.subscribe('/topic/broadcast', output => {
                subscribeBroadcast(JSON.parse(output.body));
            });
            // 接受私聊消息
            client.current.subscribe('/user/queue/messages', output => {
                subscribeMessages(JSON.parse(output.body));
            });

            // 主动通知上线
            client.current.send("/app/signup", {}, "");
        }, function (err) {
            whenError(err);
        });
    }
}