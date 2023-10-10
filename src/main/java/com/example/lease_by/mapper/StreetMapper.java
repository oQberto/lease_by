package com.example.lease_by.mapper;

import com.example.lease_by.dto.StreetDto;
import com.example.lease_by.model.entity.Street;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface StreetMapper {

    StreetDto mapToStreetDto(Street entity);

    Street mapToStreet(StreetDto dto);
}
