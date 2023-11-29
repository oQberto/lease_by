package com.example.lease_by.service;

import com.example.lease_by.dto.filter.RentalFilter;
import com.example.lease_by.dto.rental.RentalCreateEditDto;
import com.example.lease_by.dto.rental.RentalReadDto;
import com.example.lease_by.model.entity.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RentalService {

    List<RentalReadDto> getRentalsByStatus(Status status);

    Page<RentalReadDto> getAllRentalsByCityName(String cityNme, Pageable pageable);

    Page<RentalReadDto> getFilteredRentals(String cityName, RentalFilter rentalFilter, Pageable pageable);

    Page<RentalReadDto> getRentalsByAddress(String address, Pageable pageable);

    List<RentalReadDto> getRentalsByUsername(String username);

    Set<String> getRentalsBy(String address, Pageable pageable);

    Optional<RentalReadDto> getRentalById(Long id);

    Optional<RentalReadDto> createRental(RentalCreateEditDto dto, String userEmail);

    void removeRental(RentalReadDto rentalReadDto);
}
