package com.example.lease_by.service.impl;

import com.example.lease_by.dto.AddressDto;
import com.example.lease_by.dto.RentalCreateEditDto;
import com.example.lease_by.mapper.AddressMapper;
import com.example.lease_by.model.repository.AddressRepository;
import com.example.lease_by.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Optionals;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    @Override
    @Transactional
    public Optional<AddressDto> createAddress(RentalCreateEditDto dto) {
        return Optionals.firstNonEmpty(
                () -> {
                    var houseNo = dto.getHouseNo();
                    var streetName = dto.getAddress().split(", ")[0];
                    var cityName = dto.getAddress().split(", ")[1];

                    return getAddressBy(houseNo, cityName, streetName);
                },
                () -> Optional.ofNullable(addressMapper.mapToAddressDto(dto))
                        .map(addressMapper::mapToAddress)
                        .map(addressRepository::saveAndFlush)
                        .map(addressMapper::mapToAddressDto)
        );
    }

    @Override
    public Optional<AddressDto> getAddressBy(Integer houseNo, String cityName, String streetName) {
        return addressRepository
                .findAddressByHouseNoAndCity_NameAndStreet_Name(houseNo, cityName, streetName)
                .map(addressMapper::mapToAddressDto);
    }
}
