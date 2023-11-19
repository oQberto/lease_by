package com.example.lease_by.mapper;

import com.example.lease_by.dto.address.street.StreetDto;
import com.example.lease_by.model.entity.Street;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
@Component
public interface StreetMapper {

    StreetDto mapToStreetDto(Street entity);

    Street mapToStreet(StreetDto dto);
}
