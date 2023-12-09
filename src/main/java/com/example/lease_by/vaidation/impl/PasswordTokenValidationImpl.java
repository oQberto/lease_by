package com.example.lease_by.vaidation.impl;

import com.example.lease_by.model.entity.VerificationToken;
import com.example.lease_by.model.repository.VerificationTokenRepository;
import com.example.lease_by.vaidation.PasswordTokenValidation;
import com.example.lease_by.vaidation.enums.PasswordTokenVerification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

import static com.example.lease_by.vaidation.enums.PasswordTokenVerification.*;

@Component
@RequiredArgsConstructor
public class PasswordTokenValidationImpl implements PasswordTokenValidation {
    private final VerificationTokenRepository passwordTokenRepository;

    @Override
    public PasswordTokenVerification validatePasswordToken(String token) {
        Optional<VerificationToken> passwordToken = passwordTokenRepository.findByToken(token);

        return !isTokenValid(passwordToken) ? INVALID
                : isExpire(passwordToken.get().getExpireDate()) ? EXPIRED
                : VALID;
    }

    private static boolean isTokenValid(Optional<VerificationToken> passwordToken) {
        return passwordToken.isPresent();
    }

    private static boolean isExpire(LocalDate tokenExpireDate) {
        return tokenExpireDate.isBefore(LocalDate.now());
    }
}
