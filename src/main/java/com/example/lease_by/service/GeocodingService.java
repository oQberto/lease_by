package com.example.lease_by.service;

import com.example.lease_by.dto.address.GeocodingDto;
import com.google.maps.errors.ApiException;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

public interface GeocodingService {

    Set<GeocodingDto> getGeocodedData(String cityName);

    Optional<GeocodingDto> getGeocodedCityCentre(String cityName)
            throws IOException, InterruptedException, ApiException;

    Optional<GeocodingDto> getGeocodedAddress(String address)
            throws IOException, InterruptedException, ApiException;
}
