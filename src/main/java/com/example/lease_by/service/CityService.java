package com.example.lease_by.service;

import com.example.lease_by.dto.CityReadDto;

import java.util.List;

public interface CityService {

    List<CityReadDto> findAll();
}
