package com.example.lease_by.mapper;

import com.example.lease_by.dto.CityReadDto;
import com.example.lease_by.model.entity.City;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CityMapper {

    CityReadDto mapToCityReadDto(City entity);

    City mapToCity(CityReadDto dto);
}
