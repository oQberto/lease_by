package com.example.lease_by.integration.service;

import com.example.lease_by.dto.*;
import com.example.lease_by.integration.IntegrationTestBase;
import com.example.lease_by.mapper.ProfileMapper;
import com.example.lease_by.mapper.UserMapper;
import com.example.lease_by.model.entity.Profile;
import com.example.lease_by.model.entity.Rental;
import com.example.lease_by.model.entity.User;
import com.example.lease_by.model.entity.enums.*;
import com.example.lease_by.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@RequiredArgsConstructor
class RentalServiceIT extends IntegrationTestBase {
    private final RentalService rentalService;

    private final UserMapper userMapper;
    private final ProfileMapper profileMapper;

    @Test
    @Disabled
    void getAllRentalsByCityName() {
    }

    @Test
    @Disabled
    void getRentalById() {
    }

    @Test
    @Disabled
    void createRental_whenRentalCreateEditDtoAndUserEmailIsValid_shouldReturnRentalReadDto() {
        User user = buildUser(1L, "user1@gmail.com", "username1",
                "1231", Role.ADMIN);
        Profile profile = buildProfile(1L, "avatar1", "firstname1",
                "lastname1", "(29)123-4567", user);
        UserReadDto userReadDto = userMapper.mapToUserReadDto(user);
        ProfileReadDto profileReadDto = profileMapper.mapToProfileReadDto(profile);

        Optional<RentalReadDto> actualResult = rentalService.createRental(getRentalCreateEditDto(), user.getUsername());

        assertAll(() -> {
            assertThat(actualResult).isPresent();
            assertThat(actualResult.get().getUserReadDto()).isEqualTo(userReadDto);
            assertThat(actualResult.get().getRentalDetailsDto()).isEqualTo(getRentalDetailsDto());
            assertThat(actualResult.get().getAddressDto()).isEqualTo(getAddressDto());
        });
    }

    @Test
    void getRentalsByStatus() {
        List<RentalReadDto> noInfoRentals = rentalService.getRentalsByStatus(Status.NO_INFO);
        List<RentalReadDto> activeRentals = rentalService.getRentalsByStatus(Status.ACTIVE);
        List<RentalReadDto> blockedRentals = rentalService.getRentalsByStatus(Status.BLOCKED);
        List<RentalReadDto> bookedRentals = rentalService.getRentalsByStatus(Status.BOOKED);
        List<RentalReadDto> pendingConfirmationRentals = rentalService.getRentalsByStatus(Status.PENDING_CONFIRMATION);
        List<RentalReadDto> draftRentals = rentalService.getRentalsByStatus(Status.DRAFT);
        List<RentalReadDto> deletedRentals = rentalService.getRentalsByStatus(Status.DELETED);

        assertAll(() -> {
            assertThat(noInfoRentals).hasSize(2);
            assertThat(activeRentals).hasSize(15);
            assertThat(blockedRentals).hasSize(2);
            assertThat(bookedRentals).hasSize(1);
            assertThat(pendingConfirmationRentals).hasSize(3);
            assertThat(draftRentals).hasSize(1);
            assertThat(deletedRentals).hasSize(1);
        });
    }

    private static RentalCreateEditDto getRentalCreateEditDto() {
        return RentalCreateEditDto.builder()
                .propertyType(PropertyType.HOUSE)
                .address("Дружная Улица, Minsk")
                .houseNo(10)
                .yearBuilt(LocalDate.now())
                .petFriendly(true)
                .furnished(Furnished.NO_FURNITURE)
                .shortTerm(false)
                .leaseTerm(LeaseTerm.MONTHLY)
                .parkingType(ParkingType.NO_PARKING)
                .price(BigDecimal.valueOf(110.2))
                .utilities(Set.of(Utility.CABLE, Utility.ELECTRICITY))
                .categories(Set.of(Category.SUBLET, Category.SENIOR_HOUSING))
                .features(Set.of(Feature.SAUNA))
                .amenities(Set.of(Amenities.CAFE, Amenities.GROCERY_STORE))
                .build();
    }

    private AddressDto getAddressDto() {
        return AddressDto.builder()
                .id(15L)
                .houseNo(10)
                .cityReadDto(CityReadDto.builder()
                        .id(1L)
                        .name("Minsk")
                        .image("minskImage")
                        .build())
                .streetDto(StreetDto.builder()
                        .id(1L)
                        .name("Дружная Улица")
                        .zipCode("220056")
                        .build())
                .rentals(List.of(
                        Rental.builder()
                                .id(15L)
                                .price(new BigDecimal("113.0"))
                                .status(Status.NO_INFO)
                                .build()
                ))
                .build();
    }

    private RentalDetailsDto getRentalDetailsDto() {
        return RentalDetailsDto.builder()
                .id(15L)
                .propertyType(PropertyType.HOUSE)
                .parkingType(ParkingType.NO_PARKING)
                .furnished(Furnished.NO_FURNITURE)
                .leaseTerm(LeaseTerm.MONTHLY)
                .shortTerm(false)
                .petFriendly(true)
                .yearBuilt(LocalDate.now())
                .utilities(Set.of(Utility.CABLE, Utility.ELECTRICITY))
                .categories(Set.of(Category.SUBLET, Category.SENIOR_HOUSING))
                .build();
    }

    private User buildUser(Long id, String email, String username,
                           String password, Role role) {
        return User.builder()
                .id(id)
                .email(email)
                .username(username)
                .password(password)
                .role(role)
                .build();
    }

    private Profile buildProfile(Long id, String avatar, String firstName,
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
}