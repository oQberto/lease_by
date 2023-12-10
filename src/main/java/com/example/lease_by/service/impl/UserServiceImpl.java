package com.example.lease_by.service.impl;

import com.example.lease_by.dto.account.*;
import com.example.lease_by.mapper.UserMapper;
import com.example.lease_by.model.entity.Profile;
import com.example.lease_by.model.entity.User;
import com.example.lease_by.model.repository.UserRepository;
import com.example.lease_by.service.ProfileService;
import com.example.lease_by.service.UserService;
import com.example.lease_by.service.VerificationTokenService;
import com.example.lease_by.service.exception.PasswordNotMatchException;
import com.example.lease_by.service.exception.PasswordUpdateException;
import com.example.lease_by.service.exception.UserUpdateException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

// TODO: refactor all Optional chains in the class

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
    public Optional<UserReadDto> updateUser(Long id, UserEditDto userEditDto) {
        isUserExists(userEditDto);

        return Optional.ofNullable(userRepository.findUserById(id)
                .map(user -> {
                    User updatedUser = userMapper.updateUser(userEditDto, user);
                    return userMapper.mapToUserReadDto(userRepository.saveAndFlush(updatedUser));
                })
                .orElseThrow(() -> new EntityNotFoundException("User with id: " + id + " not found!")));
    }

    private void isUserExists(UserEditDto userEditDto) {
        var userByEmail = userRepository.findUserByEmail(userEditDto.getEmail());
        var userByUsername = userRepository.findUserByUsername(userEditDto.getUsername());

        if (userByEmail.isPresent()) {
            throw new UserUpdateException("User with email: " + userEditDto.getEmail() + " already exists!");
        } else if (userByUsername.isPresent()) {
            throw new UserUpdateException("User with username: " + userEditDto.getUsername() + " already exists!");
        }
    }

    @Override
    @Transactional
    public Optional<UserReadDto> updatePassword(Long id, PasswordEditDto passwordEditDto) {
        checkIfNewAndConfirmPasswordsMatches(
                passwordEditDto.getNewPassword(),
                passwordEditDto.getConfirmPassword()
        );

        return Optional.ofNullable(userRepository.findUserById(id)
                .map(user -> {
                    checkIfValidOldPassword(passwordEditDto, user);

                    user.setPassword(passwordEncoder.encode(
                            passwordEditDto.getNewPassword()
                    ));

                    return userRepository.saveAndFlush(user);
                })
                .map(userMapper::mapToUserReadDto)
                .orElseThrow(() -> new EntityNotFoundException("User with id: " + id + " not found!")));
    }

    public static void checkIfNewAndConfirmPasswordsMatches(String password1, String password2) {
        if (!password1.equals(password2)) {
            throw new PasswordUpdateException("New and confirm password don't match!");
        }
    }

    private void checkIfValidOldPassword(PasswordEditDto passwordEditDto, User user) {
        if (!passwordEncoder.matches(passwordEditDto.getOldPassword(), user.getPassword())) {
            throw new PasswordUpdateException("Incorrect old password!");
        }
    }

    @Override
    @Transactional
    public Optional<UserReadDto> verifyUser(String token) {
        return userRepository.findUserBy(token)
                .map(this::updateVerificationStatus)
                .map(userMapper::mapToUserReadDto);
    }

    private User updateVerificationStatus(User user) {
        // TODO: remove a token after a verification
        user.setIsVerified(true);
        return userRepository.saveAndFlush(user);
    }

    @Override
    @Transactional
    public void saveUserPassword(PasswordDto passwordDto) {
        // TODO: use checkIfNewAndConfirmPasswordsMatches(String password1, String password2) instead if-else
        // TODO: refactor an Optional expression in the method
        if (passwordDto.getPassword().equals(passwordDto.getConfirmPassword())) {
            userRepository.findUserBy(passwordDto.getToken())
                    .ifPresent(user -> {
                        user.setPassword(passwordEncoder.encode(
                                passwordDto.getPassword()
                        ));
                        userRepository.save(user);
                    });
        } else {
            throw new PasswordNotMatchException("Passwords don't match");
        }

        verificationTokenService.removeUsedToken(passwordDto.getUsername());
    }
}
