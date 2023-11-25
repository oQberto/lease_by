package com.example.lease_by.vaidation.impl;

import com.example.lease_by.model.entity.PasswordToken;
import com.example.lease_by.model.repository.PasswordTokenRepository;
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
    private final PasswordTokenRepository passwordTokenRepository;

    @Override
    public PasswordTokenVerification validatePasswordToken(String token) {
        Optional<PasswordToken> passwordToken = passwordTokenRepository.findByToken(token);

        return !isTokenValid(passwordToken) ? INVALID
                : isExpire(passwordToken.get().getExpireDate()) ? EXPIRED
                : VALID;
    }

    private static boolean isTokenValid(Optional<PasswordToken> passwordToken) {
        return passwordToken.isPresent();
    }

    private static boolean isExpire(LocalDate tokenExpireDate) {
        return tokenExpireDate.isBefore(LocalDate.now());
    }
}
