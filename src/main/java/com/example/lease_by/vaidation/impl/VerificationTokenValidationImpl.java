package com.example.lease_by.vaidation.impl;

import com.example.lease_by.model.entity.VerificationToken;
import com.example.lease_by.model.repository.VerificationTokenRepository;
import com.example.lease_by.vaidation.VerificationTokenValidation;
import com.example.lease_by.vaidation.enums.VerificationTokenStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

import static com.example.lease_by.vaidation.enums.VerificationTokenStatus.*;

@Component
@RequiredArgsConstructor
public class VerificationTokenValidationImpl implements VerificationTokenValidation {
    private final VerificationTokenRepository passwordTokenRepository;

    @Override
    public VerificationTokenStatus validateVerificationToken(String token) {
        Optional<VerificationToken> passwordToken = passwordTokenRepository.findByToken(token);

        return !isTokenValid(passwordToken) ? INVALID
                : isExpire(passwordToken.get().getExpireDate()) ? EXPIRED
                : VALID;
    }

    private boolean isTokenValid(Optional<VerificationToken> passwordToken) {
        return passwordToken.isPresent();
    }

    private boolean isExpire(LocalDate tokenExpireDate) {
        return tokenExpireDate.isBefore(LocalDate.now());
    }
}
