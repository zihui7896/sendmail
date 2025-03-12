package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * 发送简单邮件
     *
     * @param to      收件人邮箱
     * @param subject 邮件标题
     * @param content 邮件内容
     */
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("3335914015@qq.com"); // 发件人邮箱
        message.setTo(to); // 收件人邮箱
        message.setSubject(subject); // 邮件标题
        message.setText(content); // 邮件内容
        mailSender.send(message);
    }
}