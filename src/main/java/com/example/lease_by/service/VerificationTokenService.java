package com.example.lease_by.service;

import com.example.lease_by.dto.account.UserReadDto;
import com.example.lease_by.model.entity.VerificationToken;

public interface VerificationTokenService {

    void createToken(UserReadDto userReadDto);

    VerificationToken findVerificationTokenBy(String userEmail);

    UserReadDto removeUsedToken(UserReadDto userReadDto);
}
