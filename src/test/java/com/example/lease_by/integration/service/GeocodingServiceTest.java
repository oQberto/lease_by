package com.example.lease_by.integration.service;

import com.example.lease_by.dto.GeocodingDto;
import com.example.lease_by.integration.IntegrationTestBase;
import com.example.lease_by.service.GeocodingService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class GeocodingServiceTest extends IntegrationTestBase {
    private static final String CITY_NAME = "Minsk";
    private static final String[] POINT_NAMES = {
            "vulica Niomanskaja 73, Minsk, Belarus",
            "vulica Liucynskaja 33, Minsk, Belarus",
            "vulica Družnaja 11, Baraŭliany 223053, Belarus",
            "vulica Liucynskaja 31, Minsk, Belarus",
            "Borovaya Ulitsa 12, Valer'yanovo 223053, Belarus",
            "vulica Družnaja 1, Baraŭliany 223053, Belarus",
            "vulica Nalibockaja 9, Minsk, Belarus",
            "vulica Nalibockaja 37, Minsk, Belarus",
            "vul. Lidskaja 14, Minsk, Belarus",
            "vulica Družnaja 10, Baraŭliany 223053, Belarus",
            "vulica Družnaja 12, Baraŭliany 223053, Belarus",
            "vul. Lidskaja 12, Minsk 220055, Belarus",
            "vulica Niomanskaja 66, Minsk, Belarus"
    };

    private final GeocodingService geocodingService;

    @Test
    void getGeocodedData_whenCityNameIsValid_ShouldReturnUniqueGeocodedData() {
        Set<GeocodingDto> actualResult = geocodingService.getGeocodedData(CITY_NAME);
        Set<String> actualResultPointNames = actualResult.stream()
                .map(GeocodingDto::getPointName)
                .collect(Collectors.toSet());

        assertThat(actualResult).hasSize(13);
        assertThat(actualResultPointNames).contains(POINT_NAMES);
    }
}
