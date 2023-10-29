package com.example.lease_by.mapper;

import com.example.lease_by.dto.RentalDetailsDto;
import com.example.lease_by.mapper.annotation.MapperTest;
import com.example.lease_by.model.entity.RentalDetails;
import com.example.lease_by.model.entity.enums.Furnished;
import com.example.lease_by.model.entity.enums.LeaseTerm;
import com.example.lease_by.model.entity.enums.ParkingType;
import com.example.lease_by.model.entity.enums.PropertyType;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@MapperTest
@RequiredArgsConstructor
class AboutMapperTest {
    private final RentalDetailsMapper aboutMapper;

    @Test
    void mapToAboutDto() {
        RentalDetailsDto actualResult = aboutMapper.mapToRentalDetailsDto(getAbout());

        assertThat(actualResult).isEqualTo(getAboutDto());
    }

    @Test
    void mapToAbout() {
        RentalDetails actualResult = aboutMapper.mapToRentalDetails(getAboutDto());

        assertThat(actualResult).isEqualTo(getAbout());
    }

    private RentalDetails getAbout() {
        return RentalDetails.builder()
                .id(1L)
                .propertyType(PropertyType.HOUSE)
                .parkingType(ParkingType.STREET)
                .furnished(Furnished.NO_FURNITURE)
                .leaseTerm(LeaseTerm.MONTHLY)
                .shortTerm(true)
                .yearBuilt(LocalDate.now())
                .build();
    }

    private RentalDetailsDto getAboutDto() {
        return RentalDetailsDto.builder()
                .id(1L)
                .propertyType(PropertyType.HOUSE)
                .parkingType(ParkingType.STREET)
                .furnished(Furnished.NO_FURNITURE)
                .leaseTerm(LeaseTerm.MONTHLY)
                .shortTerm(true)
                .yearBuilt(LocalDate.now())
                .build();
    }
}