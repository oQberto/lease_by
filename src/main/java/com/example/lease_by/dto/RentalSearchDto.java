package com.example.lease_by.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RentalSearchDto {
    Long cityId;
    Long streetId;
    String address;
}
