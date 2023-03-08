package com.example.controller;

import com.example.service.QRCodeService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class Home {
    private final QRCodeService qrCodeService;

    @GetMapping("/")
    public String hello(Model model) {
        model.addAttribute("hello", "hello world");
        return "index";
    }

    @GetMapping("/chat-client")
    public String chat() {
        return "chat";
    }

    @GetMapping("/chat-qr")
    public void chat(HttpServletResponse response) {
        qrCodeService.fillQRCode(response, "/chat-client");
    }
}
