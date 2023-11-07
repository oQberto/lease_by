package com.example.lease_by.model.repository;

import com.example.lease_by.model.entity.Rental;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RentalRepository extends JpaRepository<Rental, Long> {

    List<Rental> findAllByAddress_CityName(String cityName);

    Page<Rental> findRentalsByAddress_CityNameAndAddress_StreetName(String cityName, String streetName, Pageable pageable);

    Optional<Rental> findRentalById(Long id);

    @Query("""
            select concat(a.street.name, ', ', a.city.name)
            from Rental r
            join r.address a
            where concat(a.street.name, ', ', a.city.name)
                ilike :address%
            """)
    Page<String> findRentalsBy(String address, Pageable pageable);
}
