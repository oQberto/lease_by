package com.example.lease_by.api.rest;

import com.example.lease_by.dto.address.GeocodingDto;
import com.example.lease_by.dto.filter.RentalFilter;
import com.example.lease_by.service.GeocodingService;
import com.example.lease_by.service.RentalService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Set;

import static org.springframework.web.util.UriComponentsBuilder.fromPath;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/rentals")
public class RentalRestController {
    private final RentalService rentalService;
    private final GeocodingService geocodingService;

    @GetMapping("/{address}")
    public Set<String> getRentalsByAddress(@PathVariable("address") String address) {
        return rentalService.getRentalsBy(
                address,
                PageRequest.of(0, 10)
        );
    }

    @GetMapping("/geocode/{cityName}")
    public Set<GeocodingDto> geocode(@PathVariable("cityName") String cityName) {
        return geocodingService.getGeocodedData(cityName);
    }

    @SneakyThrows
    @GetMapping("/geocode/city-centre/{cityName}")
    public GeocodingDto getGeocodedCityCentre(@PathVariable("cityName") String cityName) {
        return geocodingService.getGeocodedCityCentre(cityName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @SneakyThrows
    @GetMapping("/geocode/rental-address/{address}")
    public GeocodingDto getGeocodedAddress(@PathVariable("address") String address) {
        return geocodingService.getGeocodedAddress(address)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/redirect-filter")
    public RedirectView redirectFilter(RedirectAttributes redirectAttributes,
                                       @ModelAttribute RentalFilter rentalFilter,
                                       @PageableDefault(size = 5) Pageable pageable) {
        redirectAttributes.addFlashAttribute("filter", rentalFilter);
        var builder = fromPath("/rentals/" + rentalFilter.getCityName())
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize());

        return new RedirectView(builder.toUriString());
    }
}
