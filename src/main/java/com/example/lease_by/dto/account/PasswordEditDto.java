package com.example.lease_by.dto.account;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PasswordEditDto {
    String oldPassword;
    String newPassword;
    String confirmPassword;
}
