package com.example.lease_by.integration.service;

import com.example.lease_by.dto.account.UserReadDto;
import com.example.lease_by.dto.rental.RentalCreateEditDto;
import com.example.lease_by.dto.rental.RentalDetailsDto;
import com.example.lease_by.dto.rental.RentalReadDto;
import com.example.lease_by.integration.IntegrationTestBase;
import com.example.lease_by.mapper.ProfileMapper;
import com.example.lease_by.mapper.UserMapper;
import com.example.lease_by.model.entity.Profile;
import com.example.lease_by.model.entity.User;
import com.example.lease_by.model.entity.enums.*;
import com.example.lease_by.service.RentalService;
import jakarta.persistence.EntityNotFoundException;
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

import static com.example.lease_by.integration.service.util.TestEntityBuilder.buildProfile;
import static com.example.lease_by.integration.service.util.TestEntityBuilder.buildUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.anyLong;

@RequiredArgsConstructor
class RentalServiceIT extends IntegrationTestBase {
    private final RentalService rentalService;

    private final UserMapper userMapper;
    private final ProfileMapper profileMapper;

    @Test
    void getRentalsByAddress_whenAddressExists_shouldReturnRentals() {
        var existingAddress = "Дружная Улица, Minsk";
        var pageRequest = PageRequest.of(0, 5);

        Page<RentalReadDto> actualRentals = rentalService.getRentalsByAddress(existingAddress, pageRequest);
        assertThat(actualRentals).hasSize(4);

        List<Long> rentalIds = actualRentals
                .stream()
                .map(RentalReadDto::getId)
                .toList();
        assertThat(rentalIds).contains(1L, 15L, 16L, 17L);
    }

    @Test
    @Disabled
    void createRental_whenRentalCreateEditDtoAndUserEmailIsValid_shouldReturnNewRentalReadDto() {
        User user = buildUser(1L, "user1@gmail.com", "username1",
                "1231", Role.ADMIN, false, UserStatus.ACTIVE);
        Profile profile = buildProfile(1L, "avatar1", "firstname1",
                "lastname1", "(29)123-4567", user);
        UserReadDto userReadDto = userMapper.mapToUserReadDto(user);

        Optional<RentalReadDto> actualResult = rentalService.createRental(getRentalCreateEditDto(), user.getUsername());

        assertAll(() -> {
            assertThat(actualResult).isPresent();
            assertThat(actualResult.get().getUserReadDto()).isEqualTo(userReadDto);
            assertThat(actualResult.get().getRentalDetailsDto()).isEqualTo(getRentalDetailsDto());
        });
    }

    @Test
    void createRental_whenUserEmailIsNotValid_shouldThrowEntityNotFoundException() {
        User user = buildUser(anyLong(), anyString(), anyString(),
                anyString(), Role.ADMIN, false, UserStatus.ACTIVE);

        assertThatThrownBy(() -> rentalService.createRental(getRentalCreateEditDto(), user.getEmail()))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("User with username: " + user.getEmail() + " not found");
    }

    @Test
    void getRentalsByUsername_whenUsernameExists_shouldReturnRentals() {
        var username = "username1";

        List<RentalReadDto> actualResult = rentalService.getRentalsByUsername(username);
        assertThat(actualResult).hasSize(8);

        List<Long> rentalIds = actualResult
                .stream()
                .map(RentalReadDto::getId)
                .toList();
        assertThat(rentalIds).contains(1L, 18L, 19L, 20L, 21L, 22L, 23L, 24L);
    }

    @Test
    void getRentalsByUsername_whenUsernameNotExists_shouldReturnEmptyList() {
        var username = anyString();

        List<RentalReadDto> actualResult = rentalService.getRentalsByUsername(username);
        assertThat(actualResult).isEmpty();
    }

    @Test
    void getAllRentalsByCityName_whenCityNameExists_shouldReturnRentals() {
        var cityName = "Minsk";
        PageRequest pageRequest = PageRequest.of(0, 5);

        Page<RentalReadDto> actualResult = rentalService.getAllRentalsByCityName(cityName, pageRequest);
        assertThat(actualResult).hasSize(5);

        List<Long> rentalIds = actualResult
                .stream()
                .map(RentalReadDto::getId)
                .toList();
        assertThat(rentalIds).contains(1L, 12L, 15L, 16L, 17L);
    }

    @Test
    void getRentalById_whenIdExists_shouldReturnRental() {
        var id = 1L;

        Optional<RentalReadDto> actualResult = rentalService.getRentalById(id);
        assertThat(actualResult).isPresent();
    }

    @Test
    void getRentalById_whenIdNotExists_shouldThrowEntityNotFoundException() {
        var id = Long.MAX_VALUE;

        assertThatThrownBy(() -> rentalService.getRentalById(id))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Rental with id: " + id + " not found");
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
                .propertySize(BigDecimal.valueOf(40.0))
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