package com.example.lease_by.dto;

import com.example.lease_by.model.entity.About;
import com.example.lease_by.model.entity.Address;
import com.example.lease_by.model.entity.Image;
import com.example.lease_by.model.entity.User;
import com.example.lease_by.model.entity.enums.Amenities;
import com.example.lease_by.model.entity.enums.Feature;
import com.example.lease_by.model.entity.enums.Status;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.Set;

@Value
@Builder
public class RentalReadDto {
    Long id;
    BigDecimal price;
    User user;
    Address address;
    Status status;
    About about;
    Set<Image> images;
    Set<Amenities> amenities;
    Set<Feature> features;
}
