package com.example.lease_by.mapper;

import com.example.lease_by.dto.AddressDto;
import com.example.lease_by.dto.CityReadDto;
import com.example.lease_by.dto.RentalCreateEditDto;
import com.example.lease_by.dto.StreetDto;
import com.example.lease_by.mapper.annotation.MapperTest;
import com.example.lease_by.model.entity.Address;
import com.example.lease_by.model.entity.City;
import com.example.lease_by.model.entity.Street;
import com.example.lease_by.model.entity.enums.*;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@MapperTest
@RequiredArgsConstructor
class AddressMapperTest {
    private final AddressMapper addressMapper;

    @Test
    void mapToAddressDto() {
        Street street = getStreet();
        City city = getCity();
        Address address = getAddress(city, street);
        AddressDto expectedResult = getAddressDto();

        AddressDto actualResult = addressMapper.mapToAddressDto(address);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    void mapToAddress() {
        AddressDto dto = getAddressDto();
        Street street = getStreet();
        City city = getCity();
        Address expectedResult = getAddress(city, street);

        Address actualResult = addressMapper.mapToAddress(dto);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    void mapToAddressDtoFromRentalCreateEditDto() {
        RentalCreateEditDto dto = getRentalCreateEditDto();
        AddressDto expectedResult = AddressDto.builder()
                .houseNo(10)
                .cityReadDto(CityReadDto.builder()
                        .id(1L)
                        .name("Minsk")
                        .image("minsk.webp")
                        .build())
                .streetDto(StreetDto.builder()
                        .id(1L)
                        .name("Дружная Улица")
                        .zipCode("220056")
                        .build())
                .build();

        AddressDto actualResult = addressMapper.mapToAddressDto(dto);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    private static RentalCreateEditDto getRentalCreateEditDto() {
        return RentalCreateEditDto.builder()
                .propertyType(PropertyType.HOUSE)
                .address("Дружная Улица, Minsk")
                .houseNo(10)
                .yearBuilt(LocalDate.now())
                .petFriendly(true)
                .furnished(Furnished.NO_FURNITURE)
                .shortTerm(false)
                .leaseTerm(LeaseTerm.MONTHLY)
                .parkingType(ParkingType.NO_PARKING)
                .price(BigDecimal.valueOf(110.2))
                .utilities(Set.of(Utility.CABLE, Utility.ELECTRICITY))
                .categories(Set.of(Category.SUBLET, Category.SENIOR_HOUSING))
                .features(Set.of(Feature.SAUNA))
                .amenities(Set.of(Amenities.CAFE, Amenities.GROCERY_STORE))
                .build();
    }

    private static AddressDto getAddressDto() {
        return AddressDto.builder()
                .id(1L)
                .houseNo(10)
                .cityReadDto(CityReadDto.builder()
                        .id(1L)
                        .name("City")
                        .build())
                .streetDto(StreetDto.builder()
                        .id(1L)
                        .name("street")
                        .zipCode("220055")
                        .build())
                .build();
    }

    private static Address getAddress(City city, Street street) {
        return Address.builder()
                .id(1L)
                .houseNo(10)
                .city(city)
                .street(street)
                .build();
    }

    private static City getCity() {
        return City.builder()
                .id(1L)
                .name("City")
                .build();
    }

    private static Street getStreet() {
        return Street.builder()
                .id(1L)
                .name("street")
                .zipCode("220055")
                .build();
    }

}