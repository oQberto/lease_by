package com.example.lease_by.mapper;

import com.example.lease_by.dto.account.UserCreateDto;
import com.example.lease_by.dto.account.UserEditDto;
import com.example.lease_by.dto.account.UserReadDto;
import com.example.lease_by.mapper.annotation.MapperTest;
import com.example.lease_by.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

@MapperTest
@RequiredArgsConstructor
class UserMapperTest {
    private final UserMapper userReadMapper;
    private final PasswordEncoder passwordEncoder;

    @Test
    void updateUser() {
        User expectedResult = User.builder()
                .id(1L)
                .email("dummy@gmail.com")
                .username("dummy")
                .build();
        var userEditDto = UserEditDto.builder()
                .email("dummy@gmail.com")
                .username("dummy")
                .build();

        User actualResult = userReadMapper.updateUser(userEditDto, getUser());

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    void mapToUserReadDto() {
        UserReadDto actualResult = userReadMapper.mapToUserReadDto(getUser());

        assertThat(actualResult).isEqualTo(getUserReadDto());
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
                .build();
    }

    private UserReadDto getUserReadDto() {
        return UserReadDto.builder()
                .id(1L)
                .email("email@gmail.com")
                .username("username")
                .build();
    }

    private UserCreateDto getUserCreateDto() {
        return UserCreateDto.builder()
                .email("dummy@gmail.com")
                .password("dummy")
                .username("dummy")
                .build();
    }
}