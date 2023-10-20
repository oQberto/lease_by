package com.example.lease_by.mapper;

import com.example.lease_by.dto.UserCreateDto;
import com.example.lease_by.dto.UserReadDto;
import com.example.lease_by.mapper.annotation.MapperTest;
import com.example.lease_by.model.entity.User;
import com.example.lease_by.model.entity.enums.Role;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@MapperTest
@RequiredArgsConstructor
class UserMapperTest {
    private final UserMapper userReadMapper;

    @Test
    void mapToUserReadDto() {
        UserReadDto actualResult = userReadMapper.mapToUserReadDto(getUser());

        assertThat(actualResult).isEqualTo(getUserReadDto());
    }

    @Test
    @Disabled
    void mapToUserFromUserCreateDto() {
        User expectedResult = User.builder()
                .email("dummy@gmail.com")
                .username("dummy")
                .role(Role.USER)
                .build();

        User actualResult = userReadMapper.mapToUser(getUserCreateDto());

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    void mapToUserFromUserReadDto() {
        User actualResult = userReadMapper.mapToUser(getUserReadDto());

        assertThat(actualResult).isEqualTo(getUser());
    }

    private User getUser() {
        return User.builder()
                .id(1L)
                .email("email@gmail.com")
                .username("username")
                .password("123")
                .build();
    }

    private UserReadDto getUserReadDto() {
        return UserReadDto.builder()
                .id(1L)
                .email("email@gmail.com")
                .username("username")
                .password("123")
                .build();
    }

    private UserCreateDto getUserCreateDto() {
        return UserCreateDto.builder()
                .email("dummy@gmail.com")
                .password("dummy")
                .username("dummy")
                .role(Role.USER)
                .build();
    }
}