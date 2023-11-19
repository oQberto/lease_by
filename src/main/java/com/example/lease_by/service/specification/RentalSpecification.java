package com.example.lease_by.service.specification;

import com.example.lease_by.dto.filter.RentalFilter;
import com.example.lease_by.model.entity.Rental;
import com.example.lease_by.model.entity.enums.Category;
import com.example.lease_by.model.entity.enums.Furnished;
import com.example.lease_by.model.entity.enums.PropertyType;
import com.example.lease_by.model.entity.enums.Utility;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static com.example.lease_by.dto.filter.RentalFilter.Fields.*;
import static com.example.lease_by.model.entity.Rental.Fields.price;
import static com.example.lease_by.model.entity.Rental.Fields.rentalDetails;
import static com.example.lease_by.service.specification.RentalSpecification.FilterFields.*;
import static java.util.Objects.isNull;
import static org.springframework.data.jpa.domain.Specification.where;

@UtilityClass
public class RentalSpecification {

    @NotNull
    public static Specification<Rental> filterBy(RentalFilter rentalFilter) {
        return where(hasPropertyType(rentalFilter.getPropertyType()))
                .and(hasPropertyType(rentalFilter.getPropertyType()))
                .and(hasBedrooms(rentalFilter.getCountOfBedrooms()))
                .and(hasFurnished(rentalFilter.getFurnished()))
                .and(yearBuiltGreaterThan(rentalFilter.getYearBuilt()))
                .and(hasPrice(rentalFilter.getPriceFrom(), rentalFilter.getPriceTo()))
                .and(isPetFriendly(rentalFilter.isPetFriendly()))
                .and(hasCategories(rentalFilter.getCategories()))
                .and(hasUtilities(rentalFilter.getUtilities()));
    }

    @NotNull
    @Contract(pure = true)
    private static Specification<Rental> hasPropertyType(PropertyType propertyType) {
        return (root, query, cb) -> isNull(propertyType) || propertyType.name().isEmpty()
                ? cb.conjunction()
                : cb.equal(root.get(RENTAL_DETAILS).get(PROPERTY_TYPE), propertyType);
    }

    @NotNull
    @Contract(pure = true)
    private static Specification<Rental> hasFurnished(Furnished furnished) {
        return (root, query, cb) -> isNull(furnished) || furnished.name().isEmpty()
                ? cb.conjunction()
                : cb.equal(root.get(RENTAL_DETAILS).get(FURNISHED), furnished);
    }

    @NotNull
    @Contract(pure = true)
    private static Specification<Rental> yearBuiltGreaterThan(LocalDate yearBuilt) {
        return (root, query, cb) -> isNull(yearBuilt) || yearBuilt.isAfter(LocalDate.now())
                ? cb.conjunction()
                : cb.greaterThanOrEqualTo(root.get(RENTAL_DETAILS).get(YEAR_BUILT), yearBuilt);
    }

    @NotNull
    @Contract(pure = true)
    private static Specification<Rental> hasBedrooms(Integer bedrooms) {
        return (root, query, cb) -> bedrooms == null || bedrooms <= 0
                ? cb.conjunction()
                : cb.equal(root.get(COUNT_OF_BEDROOMS), bedrooms);
    }

    @NotNull
    @Contract(pure = true)
    private static Specification<Rental> hasPrice(BigDecimal from, BigDecimal to) {
        return (root, query, cb) -> isNull(from) || isNull(to)
                ? cb.conjunction()
                : cb.between(root.get(PRICE), from, to);
    }

    @NotNull
    @Contract(pure = true)
    private static Specification<Rental> isPetFriendly(boolean isPetFriendly) {
        return (root, query, cb) -> isPetFriendly
                ? cb.isTrue(root.get(RENTAL_DETAILS).get(PET_FRIENDLY))
                : cb.isFalse(root.get(RENTAL_DETAILS).get(PET_FRIENDLY));
    }

    @NotNull
    @Contract(pure = true)
    private static Specification<Rental> hasCategories(Set<Category> categories) {
        return (root, query, cb) -> isNull(categories) || categories.isEmpty()
                ? cb.conjunction()
                : cb.equal(root.get(RENTAL_DETAILS).get(CATEGORIES), categories);
    }

    @NotNull
    @Contract(pure = true)
    private static Specification<Rental> hasUtilities(Set<Utility> utilities) {
        return (root, query, cb) -> isNull(utilities) || utilities.isEmpty()
                ? cb.conjunction()
                : cb.equal(root.get(RENTAL_DETAILS).get(UTILITIES), utilities);
    }

    protected interface FilterFields {
        String PRICE = price;
        String UTILITIES = utilities;
        String FURNISHED = furnished;
        String YEAR_BUILT = yearBuilt;
        String CATEGORIES = categories;
        String PET_FRIENDLY = petFriendly;
        String PROPERTY_TYPE = propertyType;
        String RENTAL_DETAILS = rentalDetails;
        String COUNT_OF_BEDROOMS = countOfBedrooms;
    }
}
