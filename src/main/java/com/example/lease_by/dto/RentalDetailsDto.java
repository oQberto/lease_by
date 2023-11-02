package com.example.lease_by.dto;

import com.example.lease_by.model.entity.enums.*;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Value
@Builder
public class RentalDetailsDto {
    Long id;
    PropertyType propertyType;
    ParkingType  parkingType;
    Furnished furnished;
    LeaseTerm leaseTerm;
    Boolean shortTerm;
    Boolean petFriendly;
    LocalDate yearBuilt;
    RentalReadDto rentalReadDto;

    @Builder.Default
    Set<Category> categories = new HashSet<>();

    @Builder.Default
    Set<Utility> utilities = new HashSet<>();
}
