package com.example.lease_by.integration.service;

import com.example.lease_by.dto.ProfileCreateDto;
import com.example.lease_by.dto.UserCreateDto;
import com.example.lease_by.integration.IntegrationTestBase;
import com.example.lease_by.model.entity.Profile;
import com.example.lease_by.model.entity.User;
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

        User user = User.builder()
                .id(1L)
                .email("user1@gmail.com")
                .username("username1")
                .password("1231")
                .role(Role.ADMIN)
                .profile(profile.get())
                .build();

        Optional<User> actualResult = userService.getUserById(USER_ID);
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

        Optional<User> actualUser = userService.getUserById(16L);
        assertThat(actualUser).isPresent();

        Optional<Profile> actualProfile = profileService.getProfileById(actualUser.get().getProfile().getId());
        assertThat(actualProfile).isPresent();
        assertThat(actualProfile.get().getUser()).isEqualTo(actualUser.get());
    }
}