package com.example.lease_by.service.impl;

import com.example.lease_by.dto.account.*;
import com.example.lease_by.mapper.UserMapper;
import com.example.lease_by.model.entity.Profile;
import com.example.lease_by.model.entity.User;
import com.example.lease_by.model.entity.enums.Role;
import com.example.lease_by.model.entity.enums.UserNetworkStatus;
import com.example.lease_by.model.entity.enums.UserStatus;
import com.example.lease_by.model.repository.UserRepository;
import com.example.lease_by.service.ProfileService;
import com.example.lease_by.service.UserService;
import com.example.lease_by.service.VerificationTokenService;
import com.example.lease_by.service.exception.PasswordUpdateException;
import com.example.lease_by.service.exception.UserUpdateException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ProfileService profileService;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenService verificationTokenService;

    private final UserMapper userMapper;

    @Override
    public Optional<UserReadDto> getUserByEmail(String email) {
        return Optional.ofNullable(userRepository.findUserByEmail(email)
                .map(userMapper::mapToUserReadDto)
                .orElseThrow(() -> new EntityNotFoundException("User with email: " + email + " not found")));
    }

    @Override
    public Optional<UserReadDto> getUserByUsername(String username) {
        return Optional.ofNullable(userRepository.findUserByUsername(username)
                .map(userMapper::mapToUserReadDto)
                .orElseThrow(() -> new EntityNotFoundException("User with username: " + username + " not found")));
    }

    @Override
    public Optional<UserReadDto> getUserById(Long id) {
        return Optional.ofNullable(userRepository.findUserById(id)
                .map(userMapper::mapToUserReadDto)
                .orElseThrow(() -> new EntityNotFoundException("User with id: " + id + " not found!")));
    }

    @Override
    @Transactional
    public UserReadDto createUser(UserCreateDto dto) {
        User user = userMapper.mapToUser(dto);
        userRepository.saveAndFlush(user);

        Profile profile = new Profile();
        profile.setUser(user);
        profileService.createProfile(profile);

        return userMapper.mapToUserReadDto(user);
    }

    @Override
    @Transactional
    public UserReadDto updateUser(Long id, UserEditDto userEditDto) {
        return userRepository.findUserById(id)
                .map(user -> userMapper.updateUser(userEditDto, user))
                .map(userRepository::saveAndFlush)
                .map(userMapper::mapToUserReadDto)
                .orElseThrow(() -> new UserUpdateException("Couldn't update user with id: " + id + "! User doesn't exist."));
    }

    @Override
    @Transactional
    public UserReadDto updateUserStatus(Long id, UserStatus status) {
        return userRepository.findUserById(id)
                .map(user -> setUserStatus(status, user))
                .map(userRepository::saveAndFlush)
                .map(userMapper::mapToUserReadDto)
                .orElseThrow(() -> new UserUpdateException("Couldn't update user with id: " + id + "! User doesn't exist."));
    }

    private static User setUserStatus(UserStatus status, User user) {
        user.setStatus(status);
        return user;
    }

    @Override
    public UserReadDto updateUserRole(Long id, Role role) {
        return userRepository.findUserById(id)
                .map(user -> setNewUserRole(role, user))
                .map(userRepository::saveAndFlush)
                .map(userMapper::mapToUserReadDto)
                .orElseThrow(() -> new UserUpdateException("Couldn't update user with id: " + id + "! User doesn't exist."));
    }

    private static User setNewUserRole(Role role, User user) {
        user.setRole(role);
        return user;
    }

    @Override
    @Transactional
    public void updateUserNetworkStatus(String username, UserNetworkStatus userNetworkStatus) {
        userRepository.findUserByUsername(username)
                .map(user -> {
                    user.setNetworkStatus(userNetworkStatus);
                    return user;
                })
                .map(userRepository::saveAndFlush);
    }

    @Override
    @Transactional
    public Optional<UserReadDto> updatePassword(Long id, PasswordEditDto passwordEditDto) {
        return getUserById(id)
                .filter(user -> checkIfNewAndConfirmPasswordsMatches(
                        passwordEditDto.getNewPassword(),
                        passwordEditDto.getConfirmPassword())
                )
                .map(userMapper::mapToUser)
                .filter(user -> checkIfValidOldPassword(passwordEditDto, user))
                .map(user -> setNewPassword(passwordEditDto, user))
                .map(userMapper::mapToUserReadDto);
    }

    @Override
    @Transactional
    public Optional<UserReadDto> verifyUser(String token) {
        return userRepository.findUserBy(token)
                .map(this::updateVerificationStatus)
                .map(userMapper::mapToUserReadDto)
                .map(verificationTokenService::removeUsedToken);
    }

    private User updateVerificationStatus(User user) {
        user.setIsVerified(true);
        return userRepository.saveAndFlush(user);
    }

    @Override
    @Transactional
    public void saveUserPassword(PasswordDto passwordDto) {
        userRepository.findUserBy(passwordDto.getToken())
                .filter(user -> checkIfNewAndConfirmPasswordsMatches(
                        passwordDto.getPassword(),
                        passwordDto.getConfirmPassword())
                )
                .map(user -> setNewPassword(passwordDto, user))
                .map(userMapper::mapToUserReadDto)
                .map(verificationTokenService::removeUsedToken)
                .orElseThrow(() -> new PasswordUpdateException("New and confirm password don't match!"));
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    private boolean checkIfNewAndConfirmPasswordsMatches(String password1, String password2) {
        return password1.equals(password2);
    }

    private boolean checkIfValidOldPassword(PasswordEditDto passwordEditDto, User user) {
        return passwordEncoder.matches(passwordEditDto.getOldPassword(), user.getPassword());
    }

    private User setNewPassword(PasswordDto passwordDto, User user) {
        user.setPassword(passwordEncoder.encode(
                passwordDto.getPassword()
        ));

        return userRepository.save(user);
    }

    private User setNewPassword(PasswordEditDto passwordEditDto, User user) {
        user.setPassword(passwordEncoder.encode(
                passwordEditDto.getNewPassword()
        ));

        return userRepository.save(user);
    }
}
