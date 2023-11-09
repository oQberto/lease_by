package com.example.lease_by.api.controller;

import com.example.lease_by.dto.RentalCreateEditDto;
import com.example.lease_by.dto.RentalReadDto;
import com.example.lease_by.model.entity.enums.*;
import com.example.lease_by.service.ImageService;
import com.example.lease_by.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/rentals")
public class RentalController {
    private final RentalService rentalService;
    private final ImageService imageService;

    @ResponseBody
    @GetMapping("/image/{image}")
    public Resource getFile(@PathVariable("image") String image) {
        return imageService.getImage(image);
    }

    @GetMapping("/rental")
    public String getRentalsByStatus(Model model,
                                     @RequestParam("status") String status) {
        List<RentalReadDto> rentalsByStatus = rentalService.getRentalsByStatus(Status.valueOf(status.toUpperCase()));
        model.addAttribute("rentalsByStatus", rentalsByStatus);

        return "rental/profileRentals";
    }

    @GetMapping("/post-rental")
    public String postRental(Model model,
                             @ModelAttribute("rental") RentalCreateEditDto rentalCreateEditDto) {
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

    @PostMapping("/post-rental")
    @PreAuthorize("isAuthenticated()")
    public String createRental(@AuthenticationPrincipal UserDetails userDetails,
                               @ModelAttribute("rental") RentalCreateEditDto rentalCreateEditDto,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("rental", rentalCreateEditDto);
            return "redirect:/rentals/post-rental";
        }

        rentalService.createRental(rentalCreateEditDto, userDetails.getUsername());
        return "city/cities";
    }

    @GetMapping("/{cityName}")
    public String findRentals(Model model,
                              @PathVariable("cityName") String cityName) {
        List<RentalReadDto> rentals = rentalService.getAllRentalsByCityName(cityName);
        model.addAttribute("rentals", rentals);

        return "rental/rentals";
    }

    @GetMapping("/address/{address}")
    public String findRentalsByAddress(Model model,
                                       @PathVariable("address") String address) {
        List<RentalReadDto> rentals = rentalService.getRentalsByAddress(address, PageRequest.of(0, 10));
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
