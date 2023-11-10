package com.example.lease_by.integration.service;

import com.example.lease_by.dto.ProfileCreateDto;
import com.example.lease_by.dto.ProfileReadDto;
import com.example.lease_by.dto.UserCreateDto;
import com.example.lease_by.dto.UserReadDto;
import com.example.lease_by.integration.IntegrationTestBase;
import com.example.lease_by.mapper.ProfileMapper;
import com.example.lease_by.model.entity.enums.Role;
import com.example.lease_by.service.ProfileService;
import com.example.lease_by.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RequiredArgsConstructor
class UserServiceIT extends IntegrationTestBase {
    private static final Long EXISTING_USER_ID = 1L;
    private static final Long NOT_EXISTING_USER_ID = Long.MAX_VALUE;
    private static final Long PROFILE_ID = 1L;
    private final UserService userService;
    private final ProfileService profileService;
    private final ProfileMapper profileMapper;

    @Test
    void getUserById_whenUserIdExists_shouldReturnUser() {
        var profile = profileService.getProfileById(PROFILE_ID);
        assertThat(profile).isPresent();

        UserReadDto user = UserReadDto.builder()
                .id(1L)
                .email("user1@gmail.com")
                .username("username1")
                .role(Role.ADMIN)
                .profileReadDto(ProfileReadDto.builder()
                        .id(1L)
                        .avatar("avatar1")
                        .firstname("firstname1")
                        .lastname("lastname1")
                        .phoneNumber("(29)123-4567")
                        .build())
                .build();

        Optional<UserReadDto> actualResult = userService.getUserById(EXISTING_USER_ID);
        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(user);
    }

    @Test
    void getUserById_whenUserIdDoesNotExist_shouldThrowEntityNotFoundException() {
        assertThatThrownBy(() -> userService.getUserById(NOT_EXISTING_USER_ID))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("User with id: " + NOT_EXISTING_USER_ID + " not found!");
    }

    @Test
    void createUser() {
        var userCreateDto = UserCreateDto.builder()
                .username("dummy")
                .password("dummy")
                .email("dummy@gmail.com")
                .role(Role.USER)
                .profileCreateDto(ProfileCreateDto.builder()
                        .avatar("avatar")
                        .build())
                .build();

        userService.createUser(userCreateDto);

        Optional<UserReadDto> actualUser = userService.getUserById(16L);
        assertThat(actualUser).isPresent();

        Optional<ProfileReadDto> actualProfile = profileService.getProfileById(actualUser.get().getId());
        assertThat(actualProfile).isPresent();
        assertThat(actualProfile.get().getUserReadDto()).isEqualTo(actualUser.get());
    }

    @Test
    void updateUser() {
        var existingUser = userService.getUserById(1L);
        assertThat(existingUser).isPresent();

        var userCreateDto = UserCreateDto.builder()
                .email("newMail@gmail.com")
                .username("newUserName")
                .build();

        Optional<UserReadDto> actualResult = userService.updateUser(1L, userCreateDto);
        assertThat(actualResult).isPresent();

        assertThat(actualResult).isEqualTo(userService.getUserById(1L));
    }
}