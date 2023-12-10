package com.example.lease_by.service.impl;

import com.example.lease_by.dto.email.EmailContent;
import com.example.lease_by.service.EmailService;
import com.example.lease_by.service.UserService;
import com.example.lease_by.service.VerificationTokenService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static com.example.lease_by.service.util.EmailUtil.buildSimpleMessage;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final UserService userService;
    private final JavaMailSender javaMailSender;
    private final VerificationTokenService verificationTokenService;

    @Override
    @Async
    public void sendSimpleEmailMessage(EmailContent emailContent) {
        javaMailSender.send(
                buildSimpleMessage(emailContent)
        );
    }

    @Override
    @Async
    public void sendPasswordResetMessage(EmailContent emailContent) {
        verificationTokenService.createToken(userService
                .getUserByEmail(emailContent.getReceiverEmail())
                .orElseThrow(() -> new EntityNotFoundException("User with email: " + emailContent.getReceiverEmail() + " not found!"))
        );
        String token = verificationTokenService
                .findVerificationTokenBy(emailContent.getReceiverEmail())
                .getToken();

        javaMailSender.send(
                buildSimpleMessage(token, emailContent)
        );
    }

    @Override
    @Async
    public void sendUserVerificationMessage(String userEmail) {
        String token = verificationTokenService
                .findVerificationTokenBy(userEmail)
                .getToken();

        javaMailSender.send(
                buildSimpleMessage(token, userEmail)
        );
    }
}
