package com.example.lease_by.dto;

import com.example.lease_by.model.entity.enums.Furnished;
import com.example.lease_by.model.entity.enums.ParkingType;
import com.example.lease_by.model.entity.enums.PropertyType;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class AboutDto {
    Long id;
    PropertyType propertyType;
    ParkingType  parkingType;
    Furnished furnished;
    LocalDate leaseTerm;
    LocalDate shortTerm;
    LocalDate yearBuilt;
    RentalReadDto rentalReadDto;
}
