package com.example.lease_by.model.repository;

import com.example.lease_by.model.entity.PasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordTokenRepository extends JpaRepository<PasswordToken, Long> {

    Optional<PasswordToken> findByToken(String token);

    Optional<PasswordToken> findByUserEmail(String userEmail);
}
