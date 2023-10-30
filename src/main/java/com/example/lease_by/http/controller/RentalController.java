package com.example.lease_by.http.controller;

import com.example.lease_by.dto.RentalReadDto;
import com.example.lease_by.model.entity.enums.*;
import com.example.lease_by.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/rentals")
public class RentalController {
    private final RentalService rentalService;

    @GetMapping("/post-rental")
    public String postRental(Model model,
                             @ModelAttribute("rental") RentalReadDto rentalReadDto) {
        model.addAllAttributes(Map.of(
                "propertyTypes", List.of(PropertyType.values()),
                "utilities", List.of(Utility.values()),
                "categories", List.of(Category.values()),
                "leaseTerms", List.of(LeaseTerm.values()),
                "parkingTypes", List.of(ParkingType.values()),
                "furnished", List.of(Furnished.values()),
                "features", List.of(Feature.values()),
                "amenities", List.of(Amenities.values())
        ));
        return "rental/postRental";
    }

    @GetMapping("/{cityName}")
    public String findRentals(Model model,
                              @PathVariable("cityName") String cityName) {
        List<RentalReadDto> rentals = rentalService.getAllRentalsByCityName(cityName);
        model.addAttribute("rentals", rentals);

        return "rental/rentals";
    }

    @GetMapping("/{cityName}/{rentalId}")
    public String findRentalById(Model model,
                                 @PathVariable("cityName") String cityName,
                                 @PathVariable("rentalId") Long rentalId) {
        return rentalService.getRentalById(rentalId)
                .map(rental -> {
                    model.addAttribute("rental", rental);
                    return "rental/rentalInfo";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


    //TODO: create separate tab to the user's rentals
}
