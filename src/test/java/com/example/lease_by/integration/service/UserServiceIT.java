package com.example.lease_by.integration.service;

import com.example.lease_by.dto.ProfileCreateDto;
import com.example.lease_by.dto.ProfileReadDto;
import com.example.lease_by.dto.UserCreateDto;
import com.example.lease_by.dto.UserReadDto;
import com.example.lease_by.integration.IntegrationTestBase;
import com.example.lease_by.model.entity.enums.Role;
import com.example.lease_by.service.ProfileService;
import com.example.lease_by.service.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
class UserServiceIT extends IntegrationTestBase {
    private static final Long USER_ID = 1L;
    private static final Long PROFILE_ID = 1L;
    private final UserService userService;
    private final ProfileService profileService;

    @Test
    void getUserById() {
        var profile = profileService.getProfileById(PROFILE_ID);
        assertThat(profile).isPresent();

        UserReadDto user = UserReadDto.builder()
                .id(1L)
                .email("user1@gmail.com")
                .username("username1")
                .password("1231")
                .role(Role.ADMIN)
                .build();

        Optional<UserReadDto> actualResult = userService.getUserById(USER_ID);
        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(user);
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
        assertThat(actualResult.get().getPassword()).isEqualTo(existingUser.get().getPassword());
    }
}