package com.example.lease_by.integration.service;

import com.example.lease_by.dto.account.ProfileReadDto;
import com.example.lease_by.dto.account.UserReadDto;
import com.example.lease_by.dto.address.AddressDto;
import com.example.lease_by.dto.address.city.CityReadDto;
import com.example.lease_by.dto.address.street.StreetDto;
import com.example.lease_by.dto.rental.RentalCreateEditDto;
import com.example.lease_by.dto.rental.RentalDetailsDto;
import com.example.lease_by.dto.rental.RentalReadDto;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.example.lease_by.integration.service.util.TestEntityBuilder.*;
import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@RequiredArgsConstructor
class RentalServiceIT extends IntegrationTestBase {
    private final RentalService rentalService;

    private final UserMapper userMapper;
    private final ProfileMapper profileMapper;

    @Test
    @Disabled
    void getFilteredRentals() {
        var rentalFilter = buildRentalFilter(
                PropertyType.HOUSE,
                null,
                LocalDate.of(2023, 1, 1),
                1,
                BigDecimal.valueOf(0.00),
                BigDecimal.valueOf(150.00),
                true,
                Set.of(),
                Set.of()
        );
        var pageRequest = PageRequest.of(0, 3);

        Page<RentalReadDto> actualResult = rentalService.getFilteredRentals("Minsk", rentalFilter, pageRequest);
        Set<Long> rentalIds = actualResult.stream()
                .map(RentalReadDto::getId)
                .collect(toSet());
        Set<Long> rentalDetailsIds = actualResult.stream()
                .map(RentalReadDto::getRentalDetailsDto)
                .map(RentalDetailsDto::getId)
                .collect(toSet());

        assertAll(() -> {
            assertThat(actualResult).hasSize(3);
            assertThat(rentalIds).contains(14L, 19L, 21L);
            assertThat(rentalIds).isEqualTo(rentalDetailsIds);
        });
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
            assertThat(activeRentals).hasSize(14);
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
                .countOfBedrooms(1)
                .propertySize(BigDecimal.valueOf(38.2))
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
                                .propertySize(BigDecimal.valueOf(0.00))
                                .countOfBedrooms(1)
                                .status(Status.PENDING_CONFIRMATION)
                                .build()
                ))
                .build();
    }

    private RentalDetailsDto getRentalDetailsDto() {
        return RentalDetailsDto.builder()
                .id(25L)
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
}