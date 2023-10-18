package com.example.lease_by.service;

import com.example.lease_by.model.entity.Profile;
import com.example.lease_by.model.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileService {
    private final ProfileRepository profileRepository;

    public Optional<Profile> getProfileById(Long id) {
        return profileRepository.findById(id);
    }

    @Transactional
    public void createProfile(Profile profile) {
        profileRepository.saveAndFlush(profile);
    }
}
