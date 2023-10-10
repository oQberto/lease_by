package com.example.lease_by.service;

import com.example.lease_by.dto.RentalReadDto;
import com.example.lease_by.mapper.RentalMapper;
import com.example.lease_by.model.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RentalService {
    private final RentalRepository rentalRepository;
    private final RentalMapper rentalMapper;

    public List<RentalReadDto> getAllRentalsByCityName(String cityNme) {
        return rentalRepository.findAllByAddress_CityName(cityNme).stream()
                .map(rentalMapper::mapToRentalReadDto)
                .toList();
    }
}
