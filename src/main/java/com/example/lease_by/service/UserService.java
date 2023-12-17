package com.example.lease_by.service;

import com.example.lease_by.dto.account.*;
import com.example.lease_by.model.entity.enums.UserStatus;

import java.util.Optional;

public interface UserService {

    Optional<UserReadDto> getUserByEmail(String email);

    Optional<UserReadDto> getUserByUsername(String username);

    Optional<UserReadDto> getUserById(Long id);

    UserReadDto createUser(UserCreateDto dto);

    Optional<UserReadDto> updateUser(Long id, UserEditDto userEditDto);

    UserReadDto updateUserStatus(Long id, UserStatus status);

    Optional<UserReadDto> updatePassword(Long id, PasswordEditDto passwordEditDto);

    Optional<UserReadDto> verifyUser(String token);

    void saveUserPassword(PasswordDto passwordDto);
}
