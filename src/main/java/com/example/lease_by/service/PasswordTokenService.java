package com.example.lease_by.service;

import com.example.lease_by.dto.account.UserReadDto;
import com.example.lease_by.model.entity.PasswordToken;

public interface PasswordTokenService {

    void createPasswordToken(UserReadDto userReadDto);

    PasswordToken findPasswordTokenBy(String userEmail);
}
