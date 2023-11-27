package com.example.lease_by.service.impl;

import com.example.lease_by.dto.account.PasswordDto;
import com.example.lease_by.dto.account.UserCreateDto;
import com.example.lease_by.dto.account.UserEditDto;
import com.example.lease_by.dto.account.UserReadDto;
import com.example.lease_by.mapper.UserMapper;
import com.example.lease_by.model.entity.Profile;
import com.example.lease_by.model.entity.User;
import com.example.lease_by.model.repository.UserRepository;
import com.example.lease_by.service.PasswordTokenService;
import com.example.lease_by.service.ProfileService;
import com.example.lease_by.service.UserService;
import com.example.lease_by.service.exception.PasswordNotMatchException;
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
    private final PasswordTokenService passwordTokenService;

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
        return Optional.ofNullable(userRepository.findUserById(id)
                .map(user -> {
                    User updatedUser = userMapper.updateUser(userEditDto, user);
                    return userMapper.mapToUserReadDto(userRepository.saveAndFlush(updatedUser));
                })
                .orElseThrow(() -> new EntityNotFoundException("User with id: " + id + " not found!")));
    }

    @Override
    @Transactional
    public void saveUserPassword(PasswordDto passwordDto) {
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

        passwordTokenService.removeUsedToken(passwordDto.getUsername());
    }
}
