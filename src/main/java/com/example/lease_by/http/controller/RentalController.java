package com.example.lease_by.http.controller;

import com.example.lease_by.dto.RentalReadDto;
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

@Controller
@RequiredArgsConstructor
@RequestMapping("/rentals")
public class RentalController {
    private final RentalService rentalService;

    @GetMapping("/post")
    public String postRental(Model model,
                             @ModelAttribute("rental") RentalReadDto rentalReadDto) {

        return "rental/post";
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
