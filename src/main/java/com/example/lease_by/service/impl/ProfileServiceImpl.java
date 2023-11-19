package com.example.lease_by.service.impl;

import com.example.lease_by.dto.account.ProfileCreateDto;
import com.example.lease_by.dto.account.ProfileReadDto;
import com.example.lease_by.mapper.ProfileMapper;
import com.example.lease_by.model.entity.Profile;
import com.example.lease_by.model.repository.ProfileRepository;
import com.example.lease_by.service.ProfileService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    @Override
    public Optional<ProfileReadDto> getProfileById(Long id) {
        return Optional.ofNullable(profileRepository.findById(id)
                .map(profileMapper::mapToProfileReadDto)
                .orElseThrow(() -> new EntityNotFoundException("Profile with id: " + id + " not found")));
    }

    @Override
    public Optional<ProfileReadDto> getProfileByUserId(Long id) {
        return Optional.ofNullable(profileRepository.findProfileByUserId(id)
                .map(profileMapper::mapToProfileReadDto)
                .orElseThrow(() -> new EntityNotFoundException("Profile with user id: " + id + " not found")));
    }

    @Override
    @Transactional
    public void createProfile(Profile profile) {
        profileRepository.saveAndFlush(profile);
    }

    @Override
    @Transactional
    public Optional<ProfileReadDto> updateProfile(Long id, ProfileCreateDto profileCreateDto) {
        return Optional.ofNullable(profileRepository.findById(id)
                .map(profile -> {
                    Profile updatedProfile = profileMapper.updateProfile(profileCreateDto, profile);
                    return profileMapper.mapToProfileReadDto(updatedProfile);
                })
                .orElseThrow(() -> new EntityNotFoundException("User with id: " + id + " not found")));
    }
}
