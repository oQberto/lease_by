package com.example.lease_by.dto.rental;

import com.example.lease_by.dto.account.UserReadDto;
import com.example.lease_by.model.entity.enums.*;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Value
@Builder
@FieldNameConstants
public class RentalCreateEditDto {

    PropertyType propertyType;
    String address;
    Integer houseNo;

    LocalDate yearBuilt;
    Boolean petFriendly;
    Furnished furnished;
    Boolean shortTerm;
    LeaseTerm leaseTerm;
    ParkingType parkingType;

    String description;
    String introImage;
    Integer countOfBedrooms;
    BigDecimal price;
    BigDecimal propertySize;

    UserReadDto userReadDto;

    @Builder.Default
    Set<Utility> utilities = new HashSet<>();

    @Builder.Default
    Set<Category> categories = new HashSet<>();

    @Builder.Default
    Set<MultipartFile> images = new HashSet<>();

    @Builder.Default
    Set<Feature> features = new HashSet<>();

    @Builder.Default
    Set<Amenities> amenities = new HashSet<>();

}
