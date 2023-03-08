import { covertJson, covertText, parseJson, rest, e, rState, rEffect, localName } from "../common.js";
import { client as chatClient } from "./wsClient.js";
import { NameTitle } from "./nameTitle.js";
import { MemberList } from "./memberList.js";
import { ChatRoom } from "./chatRoom.js";

const ChatBox = () => {
    // 你的名字
    let oldName = localName();
    const [mutable, setmutable] = rState(!oldName);
    const [username, setusername] = rState(oldName || 'nobody');
    const randomName = () => {
        rest.get('/base/name')
            .then(covertText)
            .then(setusername);
    };

    // 列表
    const mainWorld = 'mainWorld';
    const [members, setmembers] = rState([]);  // 所有成员
    const [roomName, setroomName] = rState(mainWorld);  // 当前聊天对象
    const [recordMap, setrecordMap] = rState({});  // 聊天记录
    const updaterecordMap = (speaker, msg) => {
        let records = recordMap[speaker];
        if (records) {
            records.push(msg);
        } else {
            recordMap[speaker] = [msg];
        }
        setrecordMap(Object.assign({}, recordMap));
    }
    // 接收ws
    const connectFuncs = {
        updateMembers: setmembers,
        subscribeBroadcast: msg => updaterecordMap(mainWorld, msg),
        subscribeMessages: msg => {
            let room = username === msg.speaker ? msg.listener : msg.speaker;
            updaterecordMap(room, msg);
        },
        whenConnect: () => setmutable(false),
        whenError: console.error
    };

    return e('div', {},
        e(NameTitle, { mutable, username, setusername, randomName }),
        e('button', {
            disabled: !mutable, onClick: () => {
                chatClient.connect(username, connectFuncs);
            }
        }, 'connect'),
        e('button', {
            disabled: mutable, onClick: () => {
                setmutable(true);
                chatClient.disconnect();
            }
        }, 'disconnect'),
        e('div', {
            style: {
                'display': 'flex', 'flex-flow': 'row wrap',
                'justify-content': 'flex-start', 'align-items': 'flex-start', 'align-content': 'flex-start'
            }
        },
            e(MemberList, {
                username, mainWorld, members, roomName, setroomName, recordMap
            }),
            e(ChatRoom, {
                username, roomName, recordMap, sendMessage: text => {
                    mainWorld === roomName ? chatClient.sendBroadcast(text) : chatClient.sendTo(roomName, text);
                }
            }),
        ),
    );
}

const renderChatBox = ele => ReactDOM.createRoot(ele).render(e(ChatBox));

export { renderChatBox };