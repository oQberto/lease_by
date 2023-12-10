package com.example.lease_by.service.util;

import com.example.lease_by.dto.email.EmailContent;
import lombok.experimental.UtilityClass;
import org.springframework.mail.SimpleMailMessage;

@UtilityClass
public class EmailUtil {
    private static final String EMAIL_THEME = "There's been interest in your lease.";
    private static final String EMAIL_THEME_RESET_PASSWORD = "Reset your password on \"Lease.by\".";
    private static final String EMAIL_THEME_USER_VERIFICATION = "Verify your account on \"Lease.by\".";
    private static final String EMAIL_MESSAGE = "Hi %s, %s has been interested in your lease. Contact him.\n Phone number: %s \n Email: %s";
    private static final String PASSWORD_RESET_MESSAGE = """
            Hi! This is your link for password reset: \n
            http://localhost:8080/accounts/user/change-password?token=%s&username=%s
            """;
    private static final String USER_VERIFICATION_MESSAGE = """
            Hi! This is your link for an account verification: \n
            http://localhost:8080/accounts/verify-user?token=%s
            """;

    public static SimpleMailMessage buildSimpleMessage(EmailContent emailContent) {
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

        return message;
    }

    public static SimpleMailMessage buildSimpleMessage(String token, EmailContent emailContent) {
        var message = new SimpleMailMessage();

        message.setSubject(EMAIL_THEME_RESET_PASSWORD);
        message.setTo(emailContent.getReceiverEmail());
        message.setText(String.format(
                PASSWORD_RESET_MESSAGE,
                token,
                emailContent.getReceiverEmail()
        ));

        return message;
    }

    public static SimpleMailMessage buildSimpleMessage(String token, String userEmail) {
        var message = new SimpleMailMessage();

        message.setSubject(EMAIL_THEME_USER_VERIFICATION);
        message.setTo(userEmail);
        message.setText(String.format(
                USER_VERIFICATION_MESSAGE,
                token
        ));

        return message;
    }
}
