package com.example.lease_by.service;

import com.example.lease_by.dto.account.PasswordDto;
import com.example.lease_by.dto.account.UserCreateDto;
import com.example.lease_by.dto.account.UserReadDto;

import java.util.Optional;

public interface UserService {

    Optional<UserReadDto> getUserByEmail(String email);

    Optional<UserReadDto> getUserByUsername(String username);

    Optional<UserReadDto> getUserById(Long id);

    UserReadDto createUser(UserCreateDto dto);

    Optional<UserReadDto> updateUser(Long id, UserCreateDto userCreateDto);

    void saveUserPassword(PasswordDto passwordDto);
}
