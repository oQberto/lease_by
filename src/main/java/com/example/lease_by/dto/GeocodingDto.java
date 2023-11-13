package com.example.lease_by.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GeocodingDto {
    Double latitude;
    Double longitude;
    String pointName;
}
