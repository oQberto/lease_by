package com.example.lease_by.mapper;

import com.example.lease_by.dto.ProfileReadDto;
import com.example.lease_by.mapper.annotation.MapperTest;
import com.example.lease_by.model.entity.Profile;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@MapperTest
@RequiredArgsConstructor
class ProfileMapperTest {
    private final ProfileMapper profileMapper;

    @Test
    void mapToProfileReadDto() {
        ProfileReadDto actualResult = profileMapper.mapToProfileReadDto(getProfile());

        assertThat(actualResult).isEqualTo(getProfileReadDto());
    }

    @Test
    void mapToProfile() {
        Profile actualResult = profileMapper.mapToProfile(getProfileReadDto());

        assertThat(actualResult).isEqualTo(getProfile());
    }

    private Profile getProfile() {
        return Profile.builder()
                .id(1L)
                .avatar("avatarImg")
                .firstname("Firstname")
                .lastname("Lastname")
                .phoneNumber("(00)000-0000")
                .build();
    }

    private ProfileReadDto getProfileReadDto() {
        return ProfileReadDto.builder()
                .id(1L)
                .avatar("avatarImg")
                .firstname("Firstname")
                .lastname("Lastname")
                .phoneNumber("(00)000-0000")
                .build();
    }
}