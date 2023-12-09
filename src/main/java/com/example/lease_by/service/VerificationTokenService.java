package com.example.lease_by.service;

import com.example.lease_by.dto.account.UserReadDto;
import com.example.lease_by.model.entity.VerificationToken;

public interface VerificationTokenService {

    void createPasswordToken(UserReadDto userReadDto);

    VerificationToken findPasswordTokenBy(String userEmail);

    void removeUsedToken(String userEmail);
}
