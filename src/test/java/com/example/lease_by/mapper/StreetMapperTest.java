package com.example.lease_by.mapper;

import com.example.lease_by.dto.address.street.StreetDto;
import com.example.lease_by.mapper.annotation.MapperTest;
import com.example.lease_by.model.entity.Street;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@MapperTest
@RequiredArgsConstructor
class StreetMapperTest {
    private final StreetMapper streetMapper;

    @Test
    void mapToStreetDto() {
        StreetDto actualResult = streetMapper.mapToStreetDto(getStreet());

        assertThat(actualResult).isEqualTo(getStreetDto());
    }

    @Test
    void mapToStreet() {
        Street actualResult = streetMapper.mapToStreet(getStreetDto());

        assertThat(actualResult).isEqualTo(getStreet());
    }

    private Street getStreet() {
        return Street.builder()
                .id(1L)
                .name("street")
                .zipCode("220055")
                .build();
    }

    private StreetDto getStreetDto() {
        return StreetDto.builder()
                .id(1L)
                .name("street")
                .zipCode("220055")
                .build();
    }
}