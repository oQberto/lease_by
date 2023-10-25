package com.example.lease_by.dto;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@Builder
@FieldNameConstants
public class ProfileCreateDto {
    String avatar;
    String firstname;
    String lastname;
    String phoneNumber;
}
