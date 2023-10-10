package com.example.lease_by.mapper;

import com.example.lease_by.dto.CityReadDto;
import com.example.lease_by.model.entity.City;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface CityMapper {

    CityReadDto mapToCityReadDto(City entity);

    City mapToCity(CityReadDto dto);
}
