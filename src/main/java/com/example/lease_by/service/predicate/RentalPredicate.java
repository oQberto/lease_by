package com.example.lease_by.service.predicate;

import com.example.lease_by.dto.filter.RentalFilter;
import com.example.lease_by.model.querydsl.QPredicate;
import com.querydsl.core.types.Predicate;
import lombok.experimental.UtilityClass;

import static com.example.lease_by.model.entity.QRental.rental;


@UtilityClass
public class RentalPredicate {

    public static Predicate filterBy(RentalFilter rentalFilter) {
        return QPredicate.builder()
                .add(rentalFilter.getCityName(), rental.address.city.name::containsIgnoreCase)
                .add(rentalFilter.getPropertyType(), rental.rentalDetails.propertyType::eq)
                .add(rentalFilter.getFurnished(), rental.rentalDetails.furnished::eq)
                .add(rentalFilter.getYearBuilt(), rental.rentalDetails.yearBuilt::after)
                .add(rentalFilter.getCountOfBedrooms(), rental.countOfBedrooms::eq)
                .add(rentalFilter.getPriceFrom(), rental.price::gt)
                .add(rentalFilter.getPriceTo(), rental.price::loe)
                .add(rentalFilter.getCategories(),
                        categories -> rental.rentalDetails.categories.any().in(categories))
                .add(rentalFilter.getUtilities(),
                        utilities -> rental.rentalDetails.utilities.any().in(utilities))
                .build();
    }
}
