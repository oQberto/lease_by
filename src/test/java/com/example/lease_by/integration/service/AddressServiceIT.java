package com.example.lease_by.integration.service;

import com.example.lease_by.dto.address.AddressDto;
import com.example.lease_by.dto.rental.RentalCreateEditDto;
import com.example.lease_by.integration.IntegrationTestBase;
import com.example.lease_by.mapper.AddressMapper;
import com.example.lease_by.model.entity.Address;
import com.example.lease_by.model.entity.enums.*;
import com.example.lease_by.model.repository.AddressRepository;
import com.example.lease_by.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
class AddressServiceIT extends IntegrationTestBase {
    private static final Long ADDRESS_ID = 1L;
    private static final Integer NOT_EXISTING_HOUSE_NO = Integer.MAX_VALUE;
    private static final Integer HOUSE_NO = 1;
    private static final String CITY_NAME = "Minsk";
    private static final String STREET_NAME = "Дружная Улица";

    private final AddressService addressService;
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    @Test
    void createAddress_whenRentalCreateEditDtoIsValid_shouldReturnNewAddressDto() {
        Optional<AddressDto> actualResult = addressService.createAddress(getRentalCreateEditDto());
        assertThat(actualResult).isPresent();

        Optional<AddressDto> expectedResult = addressService.getAddressBy(
                getRentalCreateEditDto().getHouseNo(),
                CITY_NAME,
                STREET_NAME);
        assertThat(expectedResult).isPresent();

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    void getAddressByHouseNoCityNameStreetName_whenAddressExists_shouldReturnAddressDto() {
        Optional<Address> expectedResult = addressRepository.findById(ADDRESS_ID);
        assertThat(expectedResult).isPresent();

        Optional<AddressDto> actualResult = addressService.getAddressBy(HOUSE_NO, CITY_NAME, STREET_NAME);
        assertThat(actualResult).isPresent();

        Address actualAddress = addressMapper.mapToAddress(actualResult.get());
        assertThat(actualAddress).isEqualTo(expectedResult.get());
    }

    private static RentalCreateEditDto getRentalCreateEditDto() {
        return RentalCreateEditDto.builder()
                .propertyType(PropertyType.HOUSE)
                .address(STREET_NAME + ", " + CITY_NAME)
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
}