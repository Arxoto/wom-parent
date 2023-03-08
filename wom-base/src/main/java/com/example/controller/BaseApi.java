package com.example.controller;

import com.example.common.RestErrorMessage;
import com.example.common.CommonException;
import com.example.util.RandomStringUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/base")
public class BaseApi {

    @GetMapping("/health")
    public String health() {
        return new Date().toString();
    }

    @GetMapping("/name")
    public String name() {
        return RandomStringUtil.getRandomName();
    }

    @GetMapping("/error")
    public String error() {
        throw CommonException.of("error");
    }

    @PostMapping("/message")
    public RestErrorMessage message() {
        return RestErrorMessage.of(CommonException.of("msg"));
    }
}
