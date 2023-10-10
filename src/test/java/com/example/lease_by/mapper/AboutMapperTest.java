package com.example.lease_by.mapper;

import com.example.lease_by.dto.AboutDto;
import com.example.lease_by.mapper.annotation.MapperTest;
import com.example.lease_by.model.entity.About;
import com.example.lease_by.model.entity.enums.Furnished;
import com.example.lease_by.model.entity.enums.ParkingType;
import com.example.lease_by.model.entity.enums.PropertyType;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@MapperTest
@RequiredArgsConstructor
class AboutMapperTest {
    private final AboutMapper aboutMapper;

    @Test
    void mapToAboutDto() {
        AboutDto actualResult = aboutMapper.mapToAboutDto(getAbout());

        assertThat(actualResult).isEqualTo(getAboutDto());
    }

    @Test
    void mapToAbout() {
        About actualResult = aboutMapper.mapToAbout(getAboutDto());

        assertThat(actualResult).isEqualTo(getAbout());
    }

    private About getAbout() {
        return About.builder()
                .id(1L)
                .propertyType(PropertyType.HOUSE)
                .parkingType(ParkingType.STREET)
                .furnished(Furnished.NO_FURNITURE)
                .leaseTerm(LocalDate.now())
                .shortTerm(LocalDate.now())
                .yearBuilt(LocalDate.now())
                .build();
    }

    private AboutDto getAboutDto() {
        return AboutDto.builder()
                .id(1L)
                .propertyType(PropertyType.HOUSE)
                .parkingType(ParkingType.STREET)
                .furnished(Furnished.NO_FURNITURE)
                .leaseTerm(LocalDate.now())
                .shortTerm(LocalDate.now())
                .yearBuilt(LocalDate.now())
                .build();
    }
}