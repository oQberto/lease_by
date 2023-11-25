package com.example.lease_by.api.controller;

import com.example.lease_by.dto.email.EmailContent;
import com.example.lease_by.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/email")
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@ModelAttribute EmailContent emailContent) {
        emailService.sendEmailMessage(emailContent);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
