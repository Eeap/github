package com.example.github.service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    @Value("${mail.test}")
    private String test;
    public void sendMsg() {
        MimeMessage mimeMsg = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper msgHelper = new MimeMessageHelper(mimeMsg, false);
            msgHelper.setTo(test);
            msgHelper.setSubject("test");
            msgHelper.setText("test",false);
            javaMailSender.send(mimeMsg);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
}
