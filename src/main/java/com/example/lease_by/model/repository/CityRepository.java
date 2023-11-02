package com.example.lease_by.model.repository;

import com.example.lease_by.model.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {

    Optional<City> findCityByName(String name);
}
