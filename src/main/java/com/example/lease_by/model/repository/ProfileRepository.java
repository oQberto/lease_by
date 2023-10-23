package com.example.lease_by.model.repository;

import com.example.lease_by.model.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findProfileByUserId(Long id);
}
