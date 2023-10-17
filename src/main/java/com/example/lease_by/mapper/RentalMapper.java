package com.example.lease_by.mapper;

import com.example.lease_by.dto.RentalReadDto;
import com.example.lease_by.model.entity.Rental;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(
        componentModel = SPRING,
        injectionStrategy = CONSTRUCTOR,
        uses = {
                AddressMapper.class,
                UserMapper.class,
                AboutMapper.class
        }
)
@Component
public interface RentalMapper {

    @Mappings({
            @Mapping(target = "userReadDto", source = "user"),
            @Mapping(target = "addressDto", source = "address"),
            @Mapping(target = "aboutDto", source = "about")
    })
    RentalReadDto mapToRentalReadDto(Rental entity);

    @Mappings({
            @Mapping(target = "user", source = "userReadDto"),
            @Mapping(target = "address", source = "addressDto"),
            @Mapping(target = "about", source = "aboutDto")
    })
    Rental mapToRental(RentalReadDto dto);
}
