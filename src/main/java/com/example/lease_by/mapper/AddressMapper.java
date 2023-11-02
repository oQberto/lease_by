package com.example.lease_by.mapper;

import com.example.lease_by.dto.AddressDto;
import com.example.lease_by.dto.RentalCreateEditDto;
import com.example.lease_by.model.entity.Address;
import com.example.lease_by.model.repository.CityRepository;
import com.example.lease_by.model.repository.StreetRepository;
import jakarta.persistence.EntityNotFoundException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;


@Mapper(
        componentModel = SPRING,
        injectionStrategy = CONSTRUCTOR,
        imports = {EntityNotFoundException.class},
        uses = {
                StreetMapper.class,
                CityMapper.class
        }
)
@Component
public abstract class AddressMapper {

    @Autowired
    protected CityRepository cityRepository;

    @Autowired
    protected StreetRepository streetRepository;

    @Mappings({
            @Mapping(target = "cityReadDto", source = "city"),
            @Mapping(target = "streetDto", source = "street")
    })
    public abstract AddressDto mapToAddressDto(Address entity);

    @Mappings({
            @Mapping(target = "cityReadDto",
                    expression = """
                            java(
                                cityMapper.mapToCityReadDto(cityRepository
                                                .findCityByName(dto
                                                        .getAddress()
                                                        .split(", ")[1])
                                                .orElseThrow(() -> new EntityNotFoundException("City not found!")))
                            )
                            """),
            @Mapping(target = "streetDto",
                    expression = """
                            java(
                                streetMapper.mapToStreetDto(streetRepository
                                                .findStreetByName(dto
                                                        .getAddress()
                                                        .split(", ")[0])
                                                .orElseThrow(() -> new EntityNotFoundException("Street not found!")))
                            )
                            """),
            @Mapping(target = "rentals", ignore = true)
    })
    public abstract AddressDto mapToAddressDto(RentalCreateEditDto dto);

    @Mappings({
            @Mapping(target = "city", source = "cityReadDto"),
            @Mapping(target = "street", source = "streetDto")
    })
    public abstract Address mapToAddress(AddressDto dto);
}
