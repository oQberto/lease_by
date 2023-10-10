package com.example.lease_by.mapper;

import com.example.lease_by.dto.CityReadDto;
import com.example.lease_by.mapper.annotation.MapperTest;
import com.example.lease_by.model.entity.City;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@MapperTest
@RequiredArgsConstructor
class CityMapperTest {
    private final CityMapper cityMapper;

    @Test
    void mapCityToCityReadDto() {
        City city = getCity();
        CityReadDto expectedResult = getCityReadDto();

        CityReadDto actualResult = cityMapper.mapToCityReadDto(city);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    void mapCityReadDtoToCity() {
        CityReadDto cityReadDto = getCityReadDto();
        City expectedResult = getCity();

        City actualResult = cityMapper.mapToCity(cityReadDto);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    private static CityReadDto getCityReadDto() {
        return CityReadDto.builder()
                .id(1L)
                .image("imagePath")
                .name("Minsk")
                .build();
    }

    private static City getCity() {
        return City.builder()
                .id(1L)
                .image("imagePath")
                .name("Minsk")
                .build();
    }

}