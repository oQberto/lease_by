package com.example.lease_by.dto.address;

import com.example.lease_by.dto.address.city.CityReadDto;
import com.example.lease_by.dto.address.street.StreetDto;
import com.example.lease_by.model.entity.Rental;
import lombok.Builder;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
@Builder
public class AddressDto {
    Long id;
    Integer houseNo;
    CityReadDto cityReadDto;
    StreetDto streetDto;

    @Builder.Default
    List<Rental> rentals = new ArrayList<>();
}
