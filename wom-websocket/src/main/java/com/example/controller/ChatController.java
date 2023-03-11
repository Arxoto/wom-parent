package com.example.controller;

import com.example.entity.ChatMessage;
import com.example.service.ChatRoom;
import com.example.service.MessageQueue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ChatRoom chatRoom;
    private final MessageQueue messageQueue;

    @MessageMapping("/signup")
    public void signup(SimpMessageHeaderAccessor sha) {
        Principal user = sha.getUser();
        if (user != null && user.getName() != null) {
            chatRoom.signup(user.getName());
        }
    }

    @MessageMapping("/chat")
    public void send(SimpMessageHeaderAccessor sha, @Payload ChatMessage chatMessage) {
        String self = sha.getUser().getName();
        chatMessage.setSpeaker(self);
        chatMessage.setTimestamp(System.currentTimeMillis());
//        log.info("{}:{}", self, chatMessage);
        if (!self.equals(chatMessage.getListener())) {
            messageQueue.speakTo(self, chatMessage);
        }
        messageQueue.speakTo(chatMessage.getListener(), chatMessage);
    }

    @MessageMapping("/broadcast")
    @SendTo("/topic/broadcast")  // SendTo 效果同 webSocket.convertAndSend
    public ChatMessage broadcast(SimpMessageHeaderAccessor sha, @Payload ChatMessage chatMessage) {
        chatMessage.setSpeaker(sha.getUser().getName());
        chatMessage.setTimestamp(System.currentTimeMillis());
        return chatMessage;
    }
}
