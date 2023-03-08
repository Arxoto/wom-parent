package com.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoom {
    private final Set<String> members = new ConcurrentSkipListSet<>();
    private final MessageQueue messageQueue;

    /**
     * 登录时需主动发送
     */
    public void signup(String name) {
        if (members.add(name)) {
            log.info("members signup: {}", name);
            messageQueue.members(members);
        }
    }

    /**
     * 退出时拦截 自动退出
     */
    public void logout(String name) {
        if (members.remove(name)) {
            log.info("members logout: {}", name);
            messageQueue.members(members);
        }
    }
}
