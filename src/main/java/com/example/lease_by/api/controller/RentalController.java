package com.example.lease_by.api.controller;

import com.example.lease_by.api.controller.util.PageName.City;
import com.example.lease_by.api.controller.util.PageName.Rental;
import com.example.lease_by.api.controller.util.PageName.User;
import com.example.lease_by.config.MapApiKey;
import com.example.lease_by.dto.filter.RentalFilter;
import com.example.lease_by.dto.pagination.PageResponse;
import com.example.lease_by.dto.rental.RentalCreateEditDto;
import com.example.lease_by.dto.rental.RentalReadDto;
import com.example.lease_by.model.entity.enums.*;
import com.example.lease_by.service.ImageService;
import com.example.lease_by.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

import static com.example.lease_by.api.controller.util.UrlName.RentalController.*;
import static java.util.Objects.isNull;

@Controller
@RequiredArgsConstructor
@RequestMapping(RENTALS)
public class RentalController {
    private final MapApiKey mapApiKey;
    private final RentalService rentalService;
    private final ImageService imageService;

    @ResponseBody
    @GetMapping(IMAGE_BY_PATH)
    public Resource getFile(@PathVariable("image") String image) {
        return imageService.getImage(image);
    }

    @GetMapping(RENTALS_BY_USERNAME)
    @PreAuthorize("isAuthenticated()")
    public String getUserRentals(Model model,
                                 @PathVariable("username") String username) {
        List<RentalReadDto> rentalsByUsername = rentalService.getRentalsByUsername(username);
        model.addAttribute("userRentals", rentalsByUsername);

        return User.Profile.LISTINGS;
    }

    @GetMapping(RENTAL_DETAILS_BY_RENTAL_ID)
    public String getRentalDetails(Model model,
                                   @PathVariable("id") Long rentalId) {
        return rentalService.getRentalById(rentalId)
                .map(rental -> {
                    model.addAttribute("rental", rental);
                    return Rental.RENTAL_INFO;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping(RENTAL)
    public String getRentalsByStatus(Model model,
                                     @RequestParam("status") String status) {
        List<RentalReadDto> rentalsByStatus = rentalService.getRentalsByStatus(Status.valueOf(status.toUpperCase()));
        model.addAttribute("rentalsByStatus", rentalsByStatus);

        return "rental/profileRentals";
    }

    @GetMapping(POST_RENTAL)
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
        return Rental.POST_RENTAL;
    }

    @PostMapping(POST_RENTAL)
    @PreAuthorize("isAuthenticated()")
    public String createRental(@AuthenticationPrincipal UserDetails userDetails,
                               @ModelAttribute("rental") RentalCreateEditDto rentalCreateEditDto,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("rental", rentalCreateEditDto);
            return "redirect:" + Rental.POST_RENTAL;
        }

        rentalService.createRental(rentalCreateEditDto, userDetails.getUsername());
        return City.CITIES;
    }

    @GetMapping(RENTALS_BY_CITY_NAME)
    public String findRentals(Model model,
                              @ModelAttribute("filter") RentalFilter rentalFilter,
                              @PathVariable("cityName") String cityName,
                              @PageableDefault(size = 5) Pageable pageable) {
        Page<RentalReadDto> rentals = isNull(rentalFilter)
                ? rentalService.getAllRentalsByCityName(cityName, pageable)
                : rentalService.getFilteredRentals(rentalFilter, pageable);

        model.addAttribute("rentals", PageResponse.of(rentals));
        model.addAllAttributes(Map.of(
                "propertyTypes", List.of(PropertyType.values()),
                "utilities", List.of(Utility.values()),
                "categories", List.of(Category.values()),
                "furnished", List.of(Furnished.values()),
                "bedrooms", List.of(1, 2, 3, 4)
        ));

        return Rental.RENTALS;
    }

    @GetMapping(RENTALS_BY_ADDRESS)
    public String findRentalsByAddress(Model model,
                                       @PathVariable("address") String address) {
        List<RentalReadDto> rentals = rentalService.getRentalsByAddress(address, PageRequest.of(0, 10));
        model.addAttribute("rentals", rentals);

        return Rental.RENTALS;
    }

    @GetMapping(FILTERED_RENTALS)
    public String findRentalsByFilter(Model model,
                                      @ModelAttribute("rentalFilter") RentalFilter rentalFilter,
                                      @PathVariable("cityName") String cityName,
                                      @PageableDefault(size = 5) Pageable pageable,
                                      RedirectAttributes redirectAttributes) {


        Page<RentalReadDto> filteredRentals = rentalService.getFilteredRentals(rentalFilter, pageable);
        model.addAttribute("rentals", PageResponse.of(filteredRentals));
        redirectAttributes.addFlashAttribute("rentalFilter", rentalFilter);

        return "redirect:" + RENTALS + RENTALS_BY_CITY_NAME;
    }
}
