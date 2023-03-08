package com.example.entity;

import lombok.Data;

@Data
public class ChatMessage {
    private String text;
    private Long timestamp;
    private String speaker;
    private String listener;
}
