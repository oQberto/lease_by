package com.example.lease_by.model.repository;

import com.example.lease_by.model.entity.Street;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StreetRepository extends JpaRepository<Street, Long> {

    Optional<Street> findStreetByName(String name);
}
