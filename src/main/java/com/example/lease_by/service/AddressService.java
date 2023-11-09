package com.example.lease_by.service;

import com.example.lease_by.dto.AddressDto;
import com.example.lease_by.dto.RentalCreateEditDto;

import java.util.Optional;

public interface AddressService {

    Optional<AddressDto> createAddress(RentalCreateEditDto dto);

    Optional<AddressDto> getAddressBy(Integer houseNo, String cityName, String streetName);
}
