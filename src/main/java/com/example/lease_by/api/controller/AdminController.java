package com.example.lease_by.api.controller;

import com.example.lease_by.model.entity.enums.UserStatus;
import com.example.lease_by.service.AddressService;
import com.example.lease_by.service.CityService;
import com.example.lease_by.service.RentalService;
import com.example.lease_by.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final CityService cityService;
    private final RentalService rentalService;
    private final AddressService addressService;

    @PostMapping("/update-status/{id}")
    public String updateUSerStatus(@PathVariable("id") Long id,
                                   @RequestParam("status") UserStatus status) {
        userService.updateUserStatus(id, status);

        return "redirect:/admin";
    }
}
