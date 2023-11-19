package com.example.lease_by.service;

import com.example.lease_by.dto.address.city.CityReadDto;

import java.util.List;

public interface CityService {

    List<CityReadDto> findAll();
}
