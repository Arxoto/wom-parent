package com.example.service;

import com.example.entity.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class MessageQueue {
    /**
     * 防止循环依赖
     */
    @Lazy
    @Autowired
    private SimpMessagingTemplate webSocket;

    public void members(Collection<String> members) {
        webSocket.convertAndSend("/topic/members", members);
    }

    public void speakTo(String user, ChatMessage chatMessage) {
        webSocket.convertAndSendToUser(user, "/queue/messages", chatMessage);
    }
}
