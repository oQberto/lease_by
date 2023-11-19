package com.example.lease_by.mapper;

import com.example.lease_by.dto.address.GeocodingDto;
import com.google.maps.model.GeocodingResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
@Component
public interface GeocodingMapper {

    @Mappings({
            @Mapping(target = "latitude", expression = "java(geocodingResult.geometry.location.lat)"),
            @Mapping(target = "longitude", expression = "java(geocodingResult.geometry.location.lng)"),
            @Mapping(target = "pointName", expression = "java(geocodingResult.formattedAddress)")
    })
    GeocodingDto mapToGeocodingDto(GeocodingResult geocodingResult);
}
