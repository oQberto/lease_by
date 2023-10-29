package com.example.lease_by.service;

import com.example.lease_by.dto.AddressDto;
import com.example.lease_by.dto.RentalCreateEditDto;
import com.example.lease_by.mapper.AddressMapper;
import com.example.lease_by.model.repository.AddressRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    @Transactional
    public Optional<AddressDto> createAddress(RentalCreateEditDto dto) {
        return Optional.ofNullable(addressMapper.mapToAddressDto(dto))
                .map(addressMapper::mapToAddress)
                .map(addressRepository::saveAndFlush)
                .map(addressMapper::mapToAddressDto);
    }

    public Optional<AddressDto> getAddressBy(Integer houseNo, String cityName, String streetName) {
        return Optional.ofNullable(
                addressMapper.mapToAddressDto(addressRepository
                        .findAddressBy(houseNo, cityName, streetName)
                        .orElseThrow(() ->
                                new EntityNotFoundException(
                                        "Address with houseNo: " + houseNo + ", city name: "
                                        + cityName + ", street name: " + streetName + " not found")))
        );
    }
}
