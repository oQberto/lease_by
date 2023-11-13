package com.example.lease_by.model.repository;

import com.example.lease_by.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findAddressByHouseNoAndCity_NameAndStreet_Name(Integer houseNo, String cityName, String streetName);

    Set<Address> findAddressesByCity_Name(String cityName);
}
