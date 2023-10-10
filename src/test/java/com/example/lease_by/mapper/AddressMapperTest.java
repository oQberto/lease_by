package com.example.lease_by.mapper;

import com.example.lease_by.dto.AddressDto;
import com.example.lease_by.dto.CityReadDto;
import com.example.lease_by.dto.StreetDto;
import com.example.lease_by.mapper.annotation.MapperTest;
import com.example.lease_by.model.entity.Address;
import com.example.lease_by.model.entity.City;
import com.example.lease_by.model.entity.Street;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

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