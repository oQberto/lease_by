package com.example.lease_by.integration.service.util;

import com.example.lease_by.dto.filter.RentalFilter;
import com.example.lease_by.model.entity.Profile;
import com.example.lease_by.model.entity.User;
import com.example.lease_by.model.entity.enums.*;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@UtilityClass
public class TestEntityBuilder {

    public static User buildUser(Long id, String email, String username,
                           String password, Role role, Boolean isVerified, UserStatus status) {
        return User.builder()
                .id(id)
                .email(email)
                .username(username)
                .password(password)
                .role(role)
                .status(status)
                .isVerified(isVerified)
                .build();
    }

    public static Profile buildProfile(Long id, String avatar, String firstName,
                                 String lastname, String phoneNumber, User user) {
        Profile profile = Profile.builder()
                .id(id)
                .avatar(avatar)
                .firstname(firstName)
                .lastname(lastname)
                .phoneNumber(phoneNumber)
                .build();
        profile.setUser(user);

        return profile;
    }

    public static RentalFilter buildRentalFilter(PropertyType propertyType, Furnished furnished, LocalDate yerBuilt,
                                           Integer bedrooms, BigDecimal priceFrom, BigDecimal priceTo, boolean isPetFriendly,
                                           Set<Category> categories, Set<Utility> utilities) {
        return RentalFilter.builder()
                .propertyType(propertyType)
                .furnished(furnished)
                .yearBuilt(yerBuilt)
                .countOfBedrooms(bedrooms)
                .priceFrom(priceFrom)
                .priceTo(priceTo)
                .petFriendly(isPetFriendly)
                .categories(categories)
                .utilities(utilities)
                .build();
    }
}
