package com.example.lease_by.service.impl;

import com.example.lease_by.dto.address.city.CityReadDto;
import com.example.lease_by.mapper.CityMapper;
import com.example.lease_by.model.repository.CityRepository;
import com.example.lease_by.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    @Override
    public List<CityReadDto> findAll() {
        return cityRepository.findAll().stream()
                .map(cityMapper::mapToCityReadDto)
                .toList();
    }
}
