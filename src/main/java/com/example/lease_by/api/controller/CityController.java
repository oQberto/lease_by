package com.example.lease_by.api.controller;

import com.example.lease_by.dto.address.city.CityReadDto;
import com.example.lease_by.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.example.lease_by.api.controller.util.UrlName.CityController.CITIES;

@Controller
@RequiredArgsConstructor
@RequestMapping(CITIES)
public class CityController {
    private final CityService cityService;

    @GetMapping
    public String findAllCities(Model model) {
        List<CityReadDto> cities = cityService.findAll();
        model.addAttribute("cities", cities);

        return "city/cities";
    }
}
