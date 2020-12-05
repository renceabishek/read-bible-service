package com.read.readbibleservice.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailUtilityService {

    private final JavaMailSender javaMailSender;

    public EmailUtilityService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(SimpleMailMessage email) {
        javaMailSender.send(email);
    }
}
