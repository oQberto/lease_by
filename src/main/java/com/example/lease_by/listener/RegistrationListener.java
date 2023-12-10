package com.example.lease_by.listener;

import com.example.lease_by.listener.event.RegistrationCompleteEvent;
import com.example.lease_by.service.EmailService;
import com.example.lease_by.service.VerificationTokenService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegistrationListener {
    private final VerificationTokenService verificationTokenService;
    private final EmailService emailService;

    @EventListener
    public void confirmRegistration(@NotNull RegistrationCompleteEvent event) {
        verificationTokenService.createToken(event.getUserReadDto());

        emailService.sendUserVerificationMessage(
                event.getUserReadDto().getEmail()
        );
    }
}
