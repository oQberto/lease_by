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

    PropertyType propertyType; // rentalDetails
    String address; //address
    Integer houseNo; //address

    LocalDate yearBuilt; // rentalDetails
    Boolean petFriendly; // rentalDetails
    Furnished furnished; // rentalDetails
    Boolean shortTerm; // rentalDetails
    LeaseTerm leaseTerm; // rentalDetails
    ParkingType parkingType; // rentalDetails

    String description; // rental
    String introImage; //rental
    Integer countOfBedrooms; //rental
    BigDecimal price; // rental
    BigDecimal propertySize; //rental

    UserReadDto userReadDto; // rental

    @Builder.Default
    Set<Utility> utilities = new HashSet<>(); // rentalDetails

    @Builder.Default
    Set<Category> categories = new HashSet<>(); // rentalDetails

    @Builder.Default
    Set<MultipartFile> images = new HashSet<>(); // rental

    @Builder.Default
    Set<Feature> features = new HashSet<>(); // rental

    @Builder.Default
    Set<Amenities> amenities = new HashSet<>(); // rental

}
