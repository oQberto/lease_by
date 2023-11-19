package com.example.lease_by.service;

import com.example.lease_by.dto.address.AddressDto;
import com.example.lease_by.dto.rental.RentalCreateEditDto;

import java.util.Optional;
import java.util.Set;

public interface AddressService {

    Optional<AddressDto> createAddress(RentalCreateEditDto dto);

    Optional<AddressDto> getAddressBy(Integer houseNo, String cityName, String streetName);

    Set<AddressDto> getAddressesBy(String cityName);
}
