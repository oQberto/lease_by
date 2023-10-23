package com.example.lease_by.mapper;

import com.example.lease_by.dto.ProfileCreateDto;
import com.example.lease_by.dto.ProfileReadDto;
import com.example.lease_by.mapper.annotation.MapperTest;
import com.example.lease_by.model.entity.Profile;
import com.example.lease_by.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@MapperTest
@RequiredArgsConstructor
class ProfileMapperTest {
    private final ProfileService profileService;
    private final ProfileMapper profileMapper;

    @Test
    void mapToProfileReadDto() {
        ProfileReadDto actualResult = profileMapper.mapToProfileReadDto(getProfile());

        assertThat(actualResult).isEqualTo(getProfileReadDto());
    }

    @Test
    void mapToProfileFromProfileCreateDto() {
        Profile expectedResult = Profile.builder()
                .avatar("dummy")
                .build();

        Profile actualResult = profileMapper.mapToProfile(getProfileCreateDto());

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    void mapToProfileFromProfileReadDto() {
        Profile actualResult = profileMapper.mapToProfile(getProfileReadDto());

        assertThat(actualResult).isEqualTo(getProfile());
    }

    @Test
    void updateProfile() {
        ProfileCreateDto profileCreateDto = ProfileCreateDto.builder()
                .avatar("newAvatar")
                .firstname("newFirstName")
                .lastname("newLastName")
                .build();

        Optional<ProfileReadDto> profileReadDto = profileService.getProfileById(2L);
        assertThat(profileReadDto).isPresent();
        Profile profile = profileMapper.mapToProfile(profileReadDto.get());

        Profile updatedProfile = profileMapper.updateProfile(profileCreateDto, profile);
        assertThat(updatedProfile.getId()).isEqualTo(profileReadDto.get().getId());
        assertThat(updatedProfile.getAvatar()).isEqualTo(profileCreateDto.getAvatar());
        assertThat(updatedProfile.getFirstname()).isEqualTo(profileCreateDto.getFirstname());
        assertThat(updatedProfile.getLastname()).isEqualTo(profileCreateDto.getLastname());
        assertThat(updatedProfile.getPhoneNumber()).isEqualTo(profileReadDto.get().getPhoneNumber());
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

    private static ProfileCreateDto getProfileCreateDto() {
        return ProfileCreateDto.builder()
                .avatar("dummy")
                .build();
    }
}