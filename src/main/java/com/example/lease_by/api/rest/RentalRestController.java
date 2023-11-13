package com.example.lease_by.api.rest;

import com.example.lease_by.dto.GeocodingDto;
import com.example.lease_by.service.GeocodingService;
import com.example.lease_by.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/rentals")
public class RentalRestController {
    private final RentalService rentalService;
    private final GeocodingService geocodingService;

    @GetMapping("/{address}")
    public Set<String> getRentalsByAddress(@PathVariable("address") String address) {
        return rentalService.getRentalsBy(
                address,
                PageRequest.of(0, 10)
        );
    }

    @GetMapping("/geocode/{cityName}")
    public Set<GeocodingDto> geocode(@PathVariable("cityName") String cityName) {
        return geocodingService.getGeocodedData(cityName);
    }
}
