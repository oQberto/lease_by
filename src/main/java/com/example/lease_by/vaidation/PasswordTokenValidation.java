package com.example.lease_by.vaidation;

import com.example.lease_by.vaidation.enums.PasswordTokenVerification;

public interface PasswordTokenValidation {

    PasswordTokenVerification validatePasswordToken(String token);
}
