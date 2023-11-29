package com.example.lease_by.integration.service;

import com.example.lease_by.dto.address.GeocodingDto;
import com.example.lease_by.integration.IntegrationTestBase;
import com.example.lease_by.service.GeocodingService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@RequiredArgsConstructor
public class GeocodingServiceTest extends IntegrationTestBase {
    private static final String CITY_NAME = "Minsk";
    private static final String[] POINT_NAMES = {
            "vul. Lidskaja 14, Minsk, Minskaja voblasć, Belarus",
            "vulica Družnaja 1, Baraŭliany, Minskaja voblasć 223053, Belarus",
            "vulica Nalibockaja 9, Minsk, Minskaja voblasć, Belarus",
            "vulica Družnaja 10, Baraŭliany, Minskaja voblasć 223053, Belarus",
            "vulica Družnaja 12, Baraŭliany, Minskaja voblasć 223053, Belarus",
            "vulica Nalibockaja 37, Minsk, Minskaja voblasć, Belarus",
            "vulica Družnaja 11, Baraŭliany, Minskaja voblasć 223053, Belarus",
            "Borovaya Ulitsa 12, Valer'yanovo, Minsk Region 223053, Belarus",
            "vulica Liucynskaja 33, Minsk, Minskaja voblasć, Belarus",
            "vul. Lidskaja 12, Minsk, Minskaja voblasć 220055, Belarus",
            "vulica Niomanskaja 73, Minsk, Minskaja voblasć, Belarus",
            "vulica Liucynskaja 31, Minsk, Minskaja voblasć, Belarus",
            "vulica Niomanskaja 66, Minsk, Minskaja voblasć 220063, Belarus"
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

    @Test
    @SneakyThrows
    void getGeocodedCityCentre_whenCityIsValid_shouldReturnGeocodedData() {
        Optional<GeocodingDto> actualResult = geocodingService.getGeocodedCityCentre(CITY_NAME);
        assertThat(actualResult).isPresent();

        assertAll(() -> {
            GeocodingDto geocodingDto = actualResult.get();

            assertThat(geocodingDto.getLatitude()).isEqualTo(53.9006011);
            assertThat(geocodingDto.getLongitude()).isEqualTo(27.558972);
            assertThat(geocodingDto.getPointName()).isEqualTo("Minsk, Minsk Region, Belarus");
        });


    }
}
