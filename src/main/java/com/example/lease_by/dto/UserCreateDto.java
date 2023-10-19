package com.example.lease_by.dto;

import com.example.lease_by.model.entity.enums.Role;
import com.example.lease_by.vaidation.group.CreateAction;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserCreateDto {

    @Email
    String email;

    @NotEmpty
    String username;

    @NotBlank(groups = CreateAction.class)
    String password;

    Role role;

    ProfileCreateDto profileCreateDto;
}
