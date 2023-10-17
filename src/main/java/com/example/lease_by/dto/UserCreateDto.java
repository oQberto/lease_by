package com.example.lease_by.dto;

import com.example.lease_by.model.entity.enums.Role;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserCreateDto {
    String email;
    String username;
    String password;
    Role role;
}
