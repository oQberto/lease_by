package com.example.lease_by.service;

import com.example.lease_by.dto.RentalCreateEditDto;
import com.example.lease_by.dto.RentalReadDto;
import com.example.lease_by.mapper.AddressMapper;
import com.example.lease_by.mapper.RentalDetailsMapper;
import com.example.lease_by.mapper.RentalMapper;
import com.example.lease_by.mapper.UserMapper;
import com.example.lease_by.model.entity.Rental;
import com.example.lease_by.model.entity.RentalDetails;
import com.example.lease_by.model.entity.enums.Status;
import com.example.lease_by.model.repository.RentalDetailsRepository;
import com.example.lease_by.model.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RentalService {
    private final RentalRepository rentalRepository;
    private final RentalDetailsRepository rentalDetailsRepository;

    private final UserService userService;
    private final AddressService addressService;

    private final UserMapper userMapper;
    private final RentalMapper rentalMapper;
    private final AddressMapper addressMapper;
    private final RentalDetailsMapper rentalDetailsMapper;

    public List<RentalReadDto> getAllRentalsByCityName(String cityNme) {
        return rentalRepository.findAllByAddress_CityName(cityNme).stream()
                .map(rentalMapper::mapToRentalReadDto)
                .toList();
    }

    public Optional<RentalReadDto> getRentalById(Long id) {
        return rentalRepository.findRentalById(id)
                .map(rentalMapper::mapToRentalReadDto);
    }

    @Transactional
    public Optional<RentalReadDto> createRental(RentalCreateEditDto dto, String userEmail) {
        return Optional.ofNullable(rentalMapper.mapToRental(dto))
                .map(rental -> {
                    rental.setUser(userMapper.mapToUser(
                            userService.getUserByEmail(userEmail).get()));
                    rental.setAddress(addressMapper.mapToAddress(
                            addressService.createAddress(dto).get()));
                    rental.setStatus(Status.NO_INFO);

                    rentalRepository.saveAndFlush(rental);
                    createRentalDetails(dto, rental);
                    return rental;
                }).map(rentalMapper::mapToRentalReadDto);
    }

    private void createRentalDetails(RentalCreateEditDto dto, Rental rental) {
        RentalDetails rentalDetails = rentalDetailsMapper.mapToRentalDetails(dto);
        rentalDetails.setRental(rental);
        rentalDetailsRepository.saveAndFlush(rentalDetails);
    }
}
