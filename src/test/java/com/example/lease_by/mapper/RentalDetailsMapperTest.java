package com.example.lease_by.mapper;

import com.example.lease_by.dto.RentalCreateEditDto;
import com.example.lease_by.dto.RentalDetailsDto;
import com.example.lease_by.mapper.annotation.MapperTest;
import com.example.lease_by.model.entity.RentalDetails;
import com.example.lease_by.model.entity.enums.*;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@MapperTest
@RequiredArgsConstructor
class RentalDetailsMapperTest {
    private final RentalDetailsMapper aboutMapper;

    @Test
    void mapToRentalDetailsDto() {
        RentalDetailsDto actualResult = aboutMapper.mapToRentalDetailsDto(getRentalDetails());

        assertThat(actualResult).isEqualTo(getRentalDetailsDto());
    }

    @Test
    void mapToRentalDetailsFromRentalDetailsDto() {
        RentalDetails actualResult = aboutMapper.mapToRentalDetails(getRentalDetailsDto());

        assertThat(actualResult).isEqualTo(getRentalDetails());
    }

    @Test
    void mapToRentalDetailsFromRentalCreateEditDto() {
        RentalDetails actualResult = aboutMapper.mapToRentalDetails(getRentalCreateEditDto());

        assertThat(actualResult).isEqualTo(getRentalDetails());
    }

    private RentalDetails getRentalDetails() {
        return RentalDetails.builder()
                .propertyType(PropertyType.HOUSE)
                .parkingType(ParkingType.STREET)
                .furnished(Furnished.NO_FURNITURE)
                .leaseTerm(LeaseTerm.MONTHLY)
                .shortTerm(true)
                .petFriendly(true)
                .yearBuilt(LocalDate.now())
                .utilities(Set.of(Utility.CABLE, Utility.ELECTRICITY))
                .categories(Set.of(Category.SUBLET, Category.SENIOR_HOUSING))
                .build();
    }

    private RentalDetailsDto getRentalDetailsDto() {
        return RentalDetailsDto.builder()
                .propertyType(PropertyType.HOUSE)
                .parkingType(ParkingType.STREET)
                .furnished(Furnished.NO_FURNITURE)
                .leaseTerm(LeaseTerm.MONTHLY)
                .shortTerm(true)
                .petFriendly(true)
                .yearBuilt(LocalDate.now())
                .utilities(Set.of(Utility.CABLE, Utility.ELECTRICITY))
                .categories(Set.of(Category.SUBLET, Category.SENIOR_HOUSING))
                .build();
    }

    private static RentalCreateEditDto getRentalCreateEditDto() {
        return RentalCreateEditDto.builder()
                .propertyType(PropertyType.HOUSE)
                .address("Дружная Улица, Minsk")
                .houseNo(10)
                .yearBuilt(LocalDate.now())
                .petFriendly(true)
                .furnished(Furnished.NO_FURNITURE)
                .shortTerm(true)
                .leaseTerm(LeaseTerm.MONTHLY)
                .parkingType(ParkingType.STREET)
                .price(BigDecimal.valueOf(110.2))
                .utilities(Set.of(Utility.CABLE, Utility.ELECTRICITY))
                .categories(Set.of(Category.SUBLET, Category.SENIOR_HOUSING))
                .features(Set.of(Feature.SAUNA))
                .amenities(Set.of(Amenities.CAFE, Amenities.GROCERY_STORE))
                .build();
    }
}