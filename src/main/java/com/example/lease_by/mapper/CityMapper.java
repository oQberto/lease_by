package com.example.lease_by.mapper;

import com.example.lease_by.dto.address.city.CityReadDto;
import com.example.lease_by.model.entity.City;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
@Component
public interface CityMapper {

    CityReadDto mapToCityReadDto(City entity);

    City mapToCity(CityReadDto dto);
}
