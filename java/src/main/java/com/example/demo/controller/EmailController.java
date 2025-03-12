package com.example.demo.controller;

import com.example.demo.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*") // 允许所有域名访问
@RestController

public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send-email")
    public Map<String, Object> sendEmail(@RequestBody Map<String, String> request) {
        String receiver = request.get("receiver"); // 接收邮箱
        String subject = request.get("subject"); // 邮件标题
        String content = request.get("content"); // 邮件内容
        Map<String, Object> response = new HashMap<>();

        if (receiver == null || receiver.isEmpty() ||
                subject == null || subject.isEmpty() ||
                content == null || content.isEmpty()) {
            response.put("success", false);
            response.put("message", "请填写完整信息");
            return response;
        }

        try {
            emailService.sendSimpleMail(receiver, subject, content);
            response.put("success", true);
            response.put("message", "邮件发送成功");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "邮件发送失败");
        }

        return response;
    }
}