package com.example.controller;

import com.example.common.CommonException;
import com.example.service.Piping;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * 【注意】
 * <p>
 * 1. put&get 时 put 主动触发异常退出
 * 2. com.example.entity.PipeLine#channel(int) 捕获异常会把 getClient 的 outputStream 关闭
 * 3. 直接导致 http 链接关闭 200 因此处理异常的逻辑能够捕获但是客户端收不到
 * <p>
 * 1. put&get 时 get 主动触发异常退出
 * 2. com.example.entity.PipeLine#channel(int) 捕获异常会把 putClient 的 inputStream 关闭
 * 3. putClient 流异常关闭报错 Broken pipe 异常退出
 * 4. 服务端感知客户端异常退出 报错 Connection reset by peer
 */
@RestController
@RequestMapping("/piping")
@RequiredArgsConstructor
public class Transfer {
    private final Piping piping;

    @PutMapping("/{key}")
    public void put(@PathVariable("key") String key, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        ServletOutputStream outputStream = response.getOutputStream();
        piping.channel(key, inputStream, outputStream);
    }

    @GetMapping("/{key}")
    public void get(@PathVariable("key") String key, HttpServletResponse response) throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();
        piping.channel(key, outputStream);
    }
}
