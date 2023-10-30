package com.example.lease_by.model.repository;

import com.example.lease_by.model.entity.RentalDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalDetailsRepository extends JpaRepository<RentalDetails, Long> {
}
