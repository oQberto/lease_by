package com.example.lease_by.dto.address.street;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class StreetDto {
    Long id;
    String name;
    String zipCode;
}
