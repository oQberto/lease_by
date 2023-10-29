package com.example.lease_by.mapper;

import com.example.lease_by.dto.RentalCreateEditDto;
import com.example.lease_by.dto.RentalDetailsDto;
import com.example.lease_by.model.entity.RentalDetails;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
@Component
public interface RentalDetailsMapper {

    RentalDetailsDto mapToRentalDetailsDto(RentalDetails entity);

    RentalDetails mapToRentalDetails(RentalDetailsDto dto);

    RentalDetails mapToRentalDetails(RentalCreateEditDto dto);
}
