package com.example.lease_by.mapper;

import com.example.lease_by.dto.address.GeocodingDto;
import com.example.lease_by.mapper.annotation.MapperTest;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@MapperTest
@RequiredArgsConstructor
public class GeocodingMapperTest {
    private static final String ADDRESS_FOR_GEOCODING = "ул. Нёманская, 73, Минск, Беларусь";
    private final GeocodingMapper geocodingMapper;
    private final GeoApiContext geoApiContext;

    @Test
    void mapToGeocodingDto() throws IOException, InterruptedException, ApiException {
        GeocodingDto geocodingDto = GeocodingDto.builder()
                .latitude(53.9249513)
                .longitude(27.4295729)
                .pointName("vulica Niomanskaja 73, Minsk, Minskaja voblasć, Belarus")
                .build();

        GeocodingDto actualResult = geocodingMapper.mapToGeocodingDto(
                GeocodingApi.geocode(
                        geoApiContext,
                        ADDRESS_FOR_GEOCODING
                ).await()[0]
        );

        assertThat(actualResult).isEqualTo(geocodingDto);
    }
}
