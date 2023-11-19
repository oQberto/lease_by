package com.example.lease_by.dto.address.city;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CityDto {
    Long id;
    String name;
    String image;
}
