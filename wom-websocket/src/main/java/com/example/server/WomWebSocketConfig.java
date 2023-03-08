package com.example.server;

import com.example.entity.Speaker;
import com.example.service.ChatRoom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.security.Principal;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class WomWebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private final ChatRoom chatRoom;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 配置的ws服务地址 用于ws链接
        registry.addEndpoint("/chat-server")
                .setAllowedOriginPatterns("*")  // 跨域
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/queue");  // 用于订阅 topic用于广播 queue用于私聊
        registry.setApplicationDestinationPrefixes("/app");  // 以此开头的url通过MessageMapping注解来接收 用于发送
        registry.setUserDestinationPrefix("/user");  // 以此开头的url被认为是私密的 会自动生成一个唯一的url 用于私聊
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        // setUser
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (accessor == null) {
                    return message;
                }

                StompCommand command = accessor.getCommand();
                if (command == null) {
                    return message;
                }

                switch (command) {
                    case CONNECT -> {
                        // 前端 connect 时的 header
                        String username = accessor.getFirstNativeHeader("username");
                        if (accessor.isMutable() && username != null) {
                            accessor.setUser(new Speaker(username));
                        }
                    }
                    case DISCONNECT -> {
                        Principal user = accessor.getUser();
                        if (user != null && user.getName() != null) {
                            chatRoom.logout(user.getName());
                        }
                    }
                }

                return message;
            }
        });
    }

}
