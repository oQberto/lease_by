package com.example.lease_by.service;

import com.example.lease_by.dto.CityReadDto;
import com.example.lease_by.mapper.CityMapper;
import com.example.lease_by.model.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CityService {
    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    public List<CityReadDto> findAll() {
        return cityRepository.findAll().stream()
                .map(cityMapper::mapToCityReadDto)
                .toList();
    }
}
