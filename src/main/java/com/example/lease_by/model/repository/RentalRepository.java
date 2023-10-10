package com.example.lease_by.model.repository;

import com.example.lease_by.model.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {

    List<Rental> findAllByAddress_CityName(String cityName);
}
