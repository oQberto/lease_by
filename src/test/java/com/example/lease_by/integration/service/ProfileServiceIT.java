package com.example.lease_by.integration.service;

import com.example.lease_by.dto.account.ProfileCreateDto;
import com.example.lease_by.dto.account.ProfileReadDto;
import com.example.lease_by.dto.account.UserCreateDto;
import com.example.lease_by.integration.IntegrationTestBase;
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
class ProfileServiceIT extends IntegrationTestBase {
    private final UserService userService;
    private final ProfileService profileService;

    @Test
    void shouldGetProfileById_ifProfileIdExists() {
        var user = userService.getUserById(1L);
        assertThat(user).isPresent();

        var profileReadDto = ProfileReadDto.builder()
                .id(1L)
                .userReadDto(user.get())
                .avatar("avatar1")
                .firstname("firstname1")
                .lastname("lastname1")
                .phoneNumber("(29)123-4567")
                .avatar("avatar1")
                .build();

        Optional<ProfileReadDto> actualResult = profileService.getProfileById(1L);
        assertThat(actualResult).isPresent();

        assertThat(actualResult.get()).isEqualTo(profileReadDto);
    }

    @Test
    void shouldThrowEntityNotFoundException_ifProfileIdDoesNotExist() {
        assertThatThrownBy(() -> {
            Optional<ProfileReadDto> actualResult = profileService.getProfileById(999L);
            assertThat(actualResult).isEmpty();
        })
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Profile with id: 999 not found");
    }

    @Test
    void getProfileByUserId() {
        var user = userService.getUserById(1L);
        assertThat(user).isPresent();

        var profileReadDto = ProfileReadDto.builder()
                .id(1L)
                .userReadDto(user.get())
                .avatar("avatar1")
                .firstname("firstname1")
                .lastname("lastname1")
                .phoneNumber("(29)123-4567")
                .avatar("avatar1")
                .build();

        Optional<ProfileReadDto> actualResult = profileService.getProfileByUserId(1L);
        assertThat(actualResult).isPresent();

        assertThat(actualResult.get()).isEqualTo(profileReadDto);
    }

    @Test
    void shouldThrowEntityNotFoundException_ifUserIdDoesNotExist() {
        assertThatThrownBy(() -> {
            Optional<ProfileReadDto> actualResult = profileService.getProfileByUserId(999L);
            assertThat(actualResult).isEmpty();
        })
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Profile with user id: 999 not found");
    }

    @Test
    void createProfile() {
        UserCreateDto userCreateDto = UserCreateDto.builder()
                .username("username")
                .email("dummy@gmail.com")
                .password("123")
                .role(Role.USER)
                .build();

        userService.createUser(userCreateDto);

        Optional<ProfileReadDto> actualResult = profileService.getProfileById(16L);
        assertThat(actualResult).isPresent();
    }

    @Test
    void updateUser() {
        var existingProfile = profileService.getProfileById(1L);
        assertThat(existingProfile).isPresent();

        var profileCreateDto = ProfileCreateDto.builder()
                .avatar("newAvatar")
                .firstname("newFirstName")
                .lastname("newLastName")
                .build();

        Optional<ProfileReadDto> actualResult = profileService.updateProfile(1L, profileCreateDto);
        assertThat(actualResult).isPresent();

        var updatedProfile = profileService.getProfileById(1L);
        assertThat(updatedProfile).isPresent();

        assertThat(actualResult).isEqualTo(updatedProfile);
        assertThat(actualResult.get().getAvatar()).isEqualTo(updatedProfile.get().getAvatar());
        assertThat(actualResult.get().getFirstname()).isEqualTo(updatedProfile.get().getFirstname());
        assertThat(actualResult.get().getLastname()).isEqualTo(updatedProfile.get().getLastname());
    }
}