package com.example.lease_by.service.impl;

import com.example.lease_by.dto.email.EmailContent;
import com.example.lease_by.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static com.example.lease_by.service.util.EmailUtil.buildSimpleMessage;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;

    @Override
    @Async
    public void sendSimpleEmailMessage(EmailContent emailContent) {
        javaMailSender.send(
                buildSimpleMessage(emailContent)
        );
    }
}
