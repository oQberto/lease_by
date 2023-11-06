package com.example.lease_by.mapper;

import com.example.lease_by.dto.*;
import com.example.lease_by.mapper.annotation.MapperTest;
import com.example.lease_by.model.entity.*;
import com.example.lease_by.model.entity.enums.*;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@MapperTest
@RequiredArgsConstructor
class RentalMapperTest {
    private final RentalMapper rentalMapper;

    @Test
    void mapToRentalReadDto() {
        RentalReadDto actualResult = rentalMapper.mapToRentalReadDto(getRental());

        assertThat(actualResult).isEqualTo(getRentalReadDto());
    }

    @Test
    void mapToRental() {
        Rental actualResult = rentalMapper.mapToRental(getRentalReadDto());

        assertThat(actualResult).isEqualTo(getRental());
    }

    @Test
    void mapToRentalSearchDto() {
        RentalSearchDto actualResult = rentalMapper.mapTpRentalSearchDto(getRental());
        assertAll(() -> {
            assertThat(actualResult.getId()).isEqualTo(1L);
            assertThat(actualResult.getAddress()).isEqualTo("street, City");
        });
    }

    private Rental getRental() {
        return Rental.builder()
                .id(1L)
                .price(new BigDecimal("100.0"))
                .status(Status.NO_INFO)
                .user(User.builder()
                        .id(1L)
                        .email("email@gmail.com")
                        .username("username")
                        .password("123")
                        .build())
                .address(Address.builder()
                        .id(1L)
                        .houseNo(10)
                        .city(City.builder()
                                .id(1L)
                                .name("City")
                                .build())
                        .street(Street.builder()
                                .id(1L)
                                .name("street")
                                .zipCode("220055")
                                .build())
                        .build())
                .rentalDetails(RentalDetails.builder()
                        .id(1L)
                        .propertyType(PropertyType.HOUSE)
                        .parkingType(ParkingType.STREET)
                        .furnished(Furnished.NO_FURNITURE)
                        .leaseTerm(LeaseTerm.MONTHLY)
                        .shortTerm(true)
                        .yearBuilt(LocalDate.now())
                        .build())
                .build();
    }

    private RentalReadDto getRentalReadDto() {
        return RentalReadDto.builder()
                .id(1L)
                .price(new BigDecimal("100.0"))
                .status(Status.NO_INFO)
                .userReadDto(UserReadDto.builder()
                        .id(1L)
                        .email("email@gmail.com")
                        .username("username")
                        .password("123")
                        .build())
                .addressDto(AddressDto.builder()
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
                        .build())
                .rentalDetailsDto(RentalDetailsDto.builder()
                        .id(1L)
                        .propertyType(PropertyType.HOUSE)
                        .parkingType(ParkingType.STREET)
                        .furnished(Furnished.NO_FURNITURE)
                        .leaseTerm(LeaseTerm.MONTHLY)
                        .shortTerm(true)
                        .yearBuilt(LocalDate.now())
                        .build())
                .build();
    }
}