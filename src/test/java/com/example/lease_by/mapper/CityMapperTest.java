package com.example.lease_by.mapper;

import com.example.lease_by.dto.CityReadDto;
import com.example.lease_by.model.entity.City;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RequiredArgsConstructor
class CityMapperTest {
    private final CityMapper cityMapper;

    @Test
    void mapCityToCityReadDto() {
        City city = City.builder()
                .id(1L)
                .image("imagePath")
                .name("Minsk")
                .build();
        CityReadDto expectedResult = CityReadDto.builder()
                .id(1L)
                .image("imagePath")
                .name("Minsk")
                .build();

        CityReadDto actualResult = cityMapper.mapToCityReadDto(city);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    void mapCityReadDtoToCity() {
        CityReadDto cityReadDto = CityReadDto.builder()
                .id(1L)
                .image("imagePath")
                .name("Minsk")
                .build();
        City expectedResult = City.builder()
                .id(1L)
                .image("imagePath")
                .name("Minsk")
                .build();

        City actualResult = cityMapper.mapToCity(cityReadDto);
        assertThat(actualResult).isEqualTo(expectedResult);
    }
}