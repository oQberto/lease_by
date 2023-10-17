package com.example.lease_by.mapper;

import com.example.lease_by.dto.AddressDto;
import com.example.lease_by.model.entity.Address;
import org.mapstruct.*;
import org.springframework.stereotype.Component;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {
                StreetMapper.class,
                CityMapper.class
        }
)
@Component
public interface AddressMapper {

    @Mappings({
            @Mapping(target = "cityReadDto", source = "city"),
            @Mapping(target = "streetDto", source = "street")
    })
    AddressDto mapToAddressDto(Address entity);

    @Mappings({
            @Mapping(target = "city", source = "cityReadDto"),
            @Mapping(target = "street", source = "streetDto")
    })
    Address mapToAddress(AddressDto dto);
}
