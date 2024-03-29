package com.example.lease_by.integration.service;

import com.example.lease_by.dto.account.*;
import com.example.lease_by.integration.IntegrationTestBase;
import com.example.lease_by.model.entity.enums.Role;
import com.example.lease_by.model.entity.enums.UserNetworkStatus;
import com.example.lease_by.model.entity.enums.UserStatus;
import com.example.lease_by.service.ProfileService;
import com.example.lease_by.service.UserService;
import com.example.lease_by.service.exception.UserUpdateException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@RequiredArgsConstructor
class UserServiceIT extends IntegrationTestBase {

    private static final Long PROFILE_ID = 1L;
    private static final Long EXISTING_USER_ID = 1L;
    private static final Long NOT_EXISTING_USER_ID = Long.MAX_VALUE;

    private final UserService userService;
    private final ProfileService profileService;

    @Test
    void getUserById_whenUserIdExists_shouldReturnUser() {
        var profile = profileService.getProfileById(PROFILE_ID);
        assertThat(profile).isPresent();

        UserReadDto user = UserReadDto.builder()
                .id(1L)
                .email("user1@gmail.com")
                .username("username1")
                .role(Role.ADMIN)
                .status(UserStatus.ACTIVE)
                .networkStatus(UserNetworkStatus.OFFLINE)
                .isVerified(false)
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
                .hasMessageContaining(
                        String.format(Message.ENTITY_NOT_FOUND_EXCEPTION, NOT_EXISTING_USER_ID)
                );
    }

    @Test
    void getUsersByNetworkStatus_whenNetworkStatusIsOnline_shouldReturnOnlineUsers() {
        List<UserReadDto> onlineUsers = userService.getUsersByNetworkStatus(UserNetworkStatus.ONLINE);
        assertThat(onlineUsers).hasSize(9);

        List<Long> userIds = onlineUsers.stream()
                .map(UserReadDto::getId)
                .toList();

        assertAll(() -> {
                    assertThat(userIds).isNotEmpty();
                    assertThat(userIds).contains(3L, 4L, 5L, 7L, 8L, 9L, 11L, 12L, 14L);
                }
        );
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

        var userEditDto = UserEditDto.builder()
                .email("newMail@gmail.com")
                .username("newUserName")
                .build();

        UserReadDto actualResult = userService.updateUser(EXISTING_USER_ID, userEditDto);
        assertThat(actualResult).isNotNull();

        Optional<UserReadDto> expectedUser = userService.getUserById(EXISTING_USER_ID);
        assertThat(expectedUser).isPresent();
        assertThat(actualResult).isEqualTo(expectedUser.get());
    }

    @Test
    void updateUserStatus_ifUserExists_shouldReturnUpdatedUser() {
        var existingUser = userService.getUserById(EXISTING_USER_ID);
        assertThat(existingUser).isPresent();

        UserReadDto actualResult = userService.updateUserStatus(EXISTING_USER_ID, UserStatus.BLOCKED);

        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getStatus()).isEqualTo(UserStatus.BLOCKED);
    }

    @Test
    void updateUserStatus_ifUserIdDoesNotExist_throwUserUpdateException() {
        assertThatThrownBy(() -> userService.updateUserStatus(NOT_EXISTING_USER_ID, UserStatus.BLOCKED))
                .isInstanceOf(UserUpdateException.class)
                .hasMessageContaining(
                        String.format(Message.USER_UPDATE_EXCEPTION, NOT_EXISTING_USER_ID)
                );
    }

    @Test
    void updateUserRole_ifUserExists_shouldReturnUpdatedUser() {
        var existingUser = userService.getUserById(EXISTING_USER_ID);
        assertThat(existingUser).isPresent();

        UserReadDto actualResult = userService.updateUserRole(EXISTING_USER_ID, Role.USER);

        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getRole()).isEqualTo(Role.USER);
    }

    @Test
    void updateUserRole_ifUserIdDoesNotExist_throwUserUpdateException() {
        assertThatThrownBy(() -> userService.updateUserRole(NOT_EXISTING_USER_ID, Role.USER))
                .isInstanceOf(UserUpdateException.class)
                .hasMessageContaining(
                        String.format(Message.USER_UPDATE_EXCEPTION, NOT_EXISTING_USER_ID)
                );
    }

    @Test
    @Transactional
    @Rollback
    void deleteUserById_ifUserIdExists_shouldDeleteUser() {
        userService.deleteUserById(EXISTING_USER_ID);

        assertThatThrownBy(() -> userService.getUserById(EXISTING_USER_ID))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining(
                        String.format(Message.ENTITY_NOT_FOUND_EXCEPTION, 1)
                );
    }

    private interface Message {
        String ENTITY_NOT_FOUND_EXCEPTION = "User with id: %d not found!";
        String USER_UPDATE_EXCEPTION = "Couldn't update user with id: %d! User doesn't exist.";
    }
}