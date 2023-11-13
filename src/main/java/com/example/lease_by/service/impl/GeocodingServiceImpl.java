package com.example.lease_by.service.impl;

import com.example.lease_by.dto.AddressDto;
import com.example.lease_by.dto.GeocodingDto;
import com.example.lease_by.mapper.GeocodingMapper;
import com.example.lease_by.service.AddressService;
import com.example.lease_by.service.GeocodingService;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GeocodingServiceImpl implements GeocodingService {
    private static final String GEO_STREET_PATTERN = "%s, %d, %s, Беларусь";
    private static final String GEO_CITY_CENTRE_PATTERN = "%s, Беларусь";

    private final AddressService addressService;
    private final GeoApiContext geoApiContext;

    private final GeocodingMapper geocodingMapper;

    @Override
    public Set<GeocodingDto> getGeocodedData(String cityName) {
        return addressService.getAddressesBy(cityName).stream()
                .map(address -> {
                    try {
                        return geocode(address);
                    } catch (IOException | InterruptedException | ApiException e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(geocodingResult -> geocodingResult[0])
                .map(geocodingMapper::mapToGeocodingDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<GeocodingDto> getGeocodedCityCentre(String cityName) throws IOException, InterruptedException, ApiException {
        return Optional.ofNullable(
                        GeocodingApi.geocode(
                                geoApiContext,
                                String.format(
                                        GEO_CITY_CENTRE_PATTERN,
                                        cityName)
                        ).await()[0])
                .map(geocodingMapper::mapToGeocodingDto);
    }

    private GeocodingResult[] geocode(AddressDto addressDto) throws IOException, InterruptedException, ApiException {
        return GeocodingApi.geocode(
                geoApiContext,
                String.format(
                        GEO_STREET_PATTERN,
                        addressDto.getStreetDto().getName(),
                        addressDto.getHouseNo(),
                        addressDto.getCityReadDto().getName())
        ).await();
    }
}
