package com.example.lease_by.api.rest;

import com.example.lease_by.service.impl.RentalServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/rentals")
public class RentalRestController {
    private final RentalServiceImpl rentalService;

    @GetMapping("/{address}")
    @ResponseBody
    public Set<String> getRentalsByAddress(@PathVariable("address") String address) {
        PageRequest pageRequest = PageRequest.of(0, 10);

        return rentalService.getRentalsBy(address, pageRequest);
    }
}
