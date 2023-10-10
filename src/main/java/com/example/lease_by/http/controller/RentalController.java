package com.example.lease_by.http.controller;

import com.example.lease_by.dto.RentalReadDto;
import com.example.lease_by.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/rentals")
public class RentalController {
    private final RentalService rentalService;

    @GetMapping("/{cityName}")
    public String findRentals(Model model,
                              @PathVariable("cityName") String cityName) {
        List<RentalReadDto> rentals = rentalService.getAllRentalsByCityName(cityName);
        model.addAttribute("rentals", rentals);

        return "rental/rentals";
    }
}
