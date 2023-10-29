package com.example.lease_by.model.repository;

import com.example.lease_by.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findAddressBy(Integer houseNo, String cityName, String streetName);
}
