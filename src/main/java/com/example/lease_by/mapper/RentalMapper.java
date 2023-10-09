package com.example.lease_by.mapper;

import com.example.lease_by.dto.RentalReadDto;
import com.example.lease_by.model.entity.Rental;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RentalMapper {

    RentalReadDto mapToRentalReadDto(Rental entity);

    Rental mapToRental(RentalReadDto dto);
}
