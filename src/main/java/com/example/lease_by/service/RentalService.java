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
import com.example.lease_by.service.exception.AddressCreationException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RentalService {
    private final RentalRepository rentalRepository;
    private final RentalDetailsRepository rentalDetailsRepository;

    private final UserService userService;
    private final ImageService imageService;
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

    public List<RentalReadDto> getRentalsByAddress(String address, Pageable pageable) {
        return rentalRepository.findRentalsByAddress_CityNameAndAddress_StreetName(
                        address.split(", ")[1],
                        address.split(", ")[0],
                        pageable
                )
                .map(rentalMapper::mapToRentalReadDto)
                .toList();
    }

    public Optional<RentalReadDto> getRentalById(Long id) {
        return rentalRepository.findRentalById(id)
                .map(rentalMapper::mapToRentalReadDto);
    }

    public Set<String> getRentalsBy(String address, Pageable pageable) {
        return rentalRepository.findRentalsBy(address, pageable)
                .stream()
                .collect(Collectors.toSet());
    }

    @Transactional
    public Optional<RentalReadDto> createRental(RentalCreateEditDto dto, String userEmail) {
        return Optional.ofNullable(rentalMapper.mapToRental(dto))
                .map(rental -> {
                    uploadImage(dto.getImages());

                    setDependentEntitiesToRental(dto, userEmail, rental);

                    rentalRepository.saveAndFlush(rental);
                    createRentalDetails(dto, rental);
                    return rental;
                })
                .map(rentalMapper::mapToRentalReadDto);
    }

    private void setDependentEntitiesToRental(RentalCreateEditDto dto, String userEmail, Rental rental) {
        rental.setUser(userMapper.mapToUser(
                userService.getUserByEmail(userEmail)
                        .orElseThrow(() -> new EntityNotFoundException("User with email: " + userEmail + " not found")))
        );
        rental.setAddress(addressMapper.mapToAddress(
                addressService.createAddress(dto)
                        .orElseThrow(() -> new AddressCreationException("Address hasn't created: " + dto.toString())))
        );
        rental.setStatus(Status.NO_INFO);
    }

    private void uploadImage(Set<MultipartFile> images) {
        images.stream()
                .filter(Predicate.not(MultipartFile::isEmpty))
                .forEach(image -> {
                    try {
                        imageService.upload(image.getOriginalFilename(), image.getInputStream());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    private void createRentalDetails(RentalCreateEditDto dto, Rental rental) {
        RentalDetails rentalDetails = rentalDetailsMapper.mapToRentalDetails(dto);
        rentalDetails.setRental(rental);
        rentalDetailsRepository.saveAndFlush(rentalDetails);
    }
}
