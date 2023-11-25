package com.example.lease_by.service;

import com.example.lease_by.dto.email.EmailContent;

public interface EmailService {

    void sendSimpleEmailMessage(EmailContent emailContent);

    void sendPasswordResetMessage(EmailContent emailContent);
}
