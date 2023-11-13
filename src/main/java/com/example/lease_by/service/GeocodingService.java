package com.example.lease_by.service;

import com.example.lease_by.dto.GeocodingDto;

import java.util.Set;

public interface GeocodingService {

    Set<GeocodingDto> geocodedData(String cityName);
}
