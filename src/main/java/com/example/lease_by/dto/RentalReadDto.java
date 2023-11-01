package com.example.lease_by.dto;

import com.example.lease_by.model.entity.enums.Amenities;
import com.example.lease_by.model.entity.enums.Feature;
import com.example.lease_by.model.entity.enums.Status;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Value
@Builder
public class RentalReadDto {
    Long id;
    BigDecimal price;
    UserReadDto userReadDto;
    AddressDto addressDto;
    Status status;
    String description;
    RentalDetailsDto rentalDetailsDto;

    @Builder.Default
    Map<String, String> images = new HashMap<>();

    @Builder.Default
    Set<Amenities> amenities = new HashSet<>();

    @Builder.Default
    Set<Feature> features = new HashSet<>();
}
