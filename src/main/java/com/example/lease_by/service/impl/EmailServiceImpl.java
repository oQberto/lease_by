package com.example.lease_by.service.impl;

import com.example.lease_by.dto.email.EmailContent;
import com.example.lease_by.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private static final String EMAIL_THEME = "There's been interest in your lease.";
    private static final String EMAIL_MESSAGE = "Hi %s, %s has been interests in your lease. Contact with him.\n Phone number: %s \n Email: %s";
    private final JavaMailSender javaMailSender;

    @Override
    @Async
    public void sendEmailMessage(EmailContent emailContent) {
        var message = new SimpleMailMessage();

        message.setSubject(EMAIL_THEME);
        message.setFrom(emailContent.getSenderEmail());
        message.setTo(emailContent.getReceiverEmail());
        message.setText(String.format(
                EMAIL_MESSAGE,
                emailContent.getReceiverName(),
                emailContent.getSenderName(),
                emailContent.getPhoneNumber(),
                emailContent.getSenderEmail())
        );

        javaMailSender.send(message);
    }
}
