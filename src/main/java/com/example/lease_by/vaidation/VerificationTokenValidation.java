package com.example.lease_by.vaidation;

import com.example.lease_by.vaidation.enums.VerificationTokenStatus;

public interface VerificationTokenValidation {

    VerificationTokenStatus validateVerificationToken(String token);
}
