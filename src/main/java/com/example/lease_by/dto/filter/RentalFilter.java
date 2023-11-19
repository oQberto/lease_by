package com.example.lease_by.dto.filter;

import com.example.lease_by.model.entity.enums.Category;
import com.example.lease_by.model.entity.enums.Furnished;
import com.example.lease_by.model.entity.enums.PropertyType;
import com.example.lease_by.model.entity.enums.Utility;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Value
@Builder
@FieldNameConstants
public class RentalFilter {

    String cityName;

    PropertyType propertyType;
    Furnished furnished;

    @PastOrPresent
    LocalDate yearBuilt;

    Integer countOfBedrooms;
    BigDecimal priceFrom;
    BigDecimal priceTo;
    Boolean petFriendly;

    Set<Category> categories;
    Set<Utility> utilities;
}
