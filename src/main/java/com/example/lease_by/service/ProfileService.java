package com.example.lease_by.service;

import com.example.lease_by.dto.account.ProfileCreateDto;
import com.example.lease_by.dto.account.ProfileReadDto;
import com.example.lease_by.model.entity.Profile;

import java.util.Optional;

public interface ProfileService {

    Optional<ProfileReadDto> getProfileById(Long id);

    Optional<ProfileReadDto> getProfileByUserId(Long id);

    void createProfile(Profile profile);

    Optional<ProfileReadDto> updateProfile(Long id, ProfileCreateDto profileCreateDto);
}
