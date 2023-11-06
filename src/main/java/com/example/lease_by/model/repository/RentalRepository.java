package com.example.lease_by.model.repository;

import com.example.lease_by.model.entity.Rental;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RentalRepository extends JpaRepository<Rental, Long> {

    List<Rental> findAllByAddress_CityName(String cityName);

    Optional<Rental> findRentalById(Long id);

    @Query("""
            from Rental r
            join r.address a
            where concat(a.street.name, ', ', a.city.name)
                like :address%
            """)
    List<Rental> findRentalsBy(String address, Pageable pageable);
}
