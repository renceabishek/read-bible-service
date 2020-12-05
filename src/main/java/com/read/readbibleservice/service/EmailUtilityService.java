package com.read.readbibleservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailUtilityService {

    @Autowired
    private JavaMailSender javaMailSender;

    public EmailUtilityService() {
    }

    public void sendEmail(SimpleMailMessage email) {
        javaMailSender.send(email);
    }
}
