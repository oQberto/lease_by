package com.example.lease_by.mapper;

import com.example.lease_by.dto.UserReadDto;
import com.example.lease_by.mapper.annotation.MapperTest;
import com.example.lease_by.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@MapperTest
@RequiredArgsConstructor
class UserReadMapperTest {
    private final UserReadMapper userReadMapper;

    @Test
    void mapToUserReadDto() {
        UserReadDto actualResult = userReadMapper.mapToUserReadDto(getUser());

        assertThat(actualResult).isEqualTo(getUserReadDto());
    }

    @Test
    void mapToUser() {
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
}