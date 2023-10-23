package com.example.lease_by.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProfileCreateDto {
    String avatar;
    String firstname;
    String lastname;
    String phoneNumber;
}
