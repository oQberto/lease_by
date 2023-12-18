package com.example.lease_by.api.controller;

import com.example.lease_by.model.entity.enums.Role;
import com.example.lease_by.model.entity.enums.UserStatus;
import com.example.lease_by.service.AddressService;
import com.example.lease_by.service.CityService;
import com.example.lease_by.service.RentalService;
import com.example.lease_by.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final UserService userService;
    private final CityService cityService;
    private final RentalService rentalService;
    private final AddressService addressService;

    //TODO: create a main page of the admin panel

    @GetMapping
    public String showAdminPanel() {
        return "admin/admin";
    }

    @PostMapping("/user/{id}/update-status")
    public String updateUserStatus(@PathVariable("id") Long id,
                                   @RequestParam("status") UserStatus status) {
        userService.updateUserStatus(id, status);

        return "redirect:/admin";
    }

    @PostMapping("/user/{id}/update-role")
    public String updateUserRole(@PathVariable("id") Long id,
                                 @RequestParam("role") Role role) {
        userService.updateUserRole(id, role);

        return "redirect:/admin";
    }

    @PostMapping("/user/{id}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);

        return "redirect:/admin";
    }
}
