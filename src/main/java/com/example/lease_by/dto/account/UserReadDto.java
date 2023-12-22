package com.example.lease_by.dto.account;

import com.example.lease_by.model.entity.enums.Role;
import com.example.lease_by.model.entity.enums.UserNetworkStatus;
import com.example.lease_by.model.entity.enums.UserStatus;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserReadDto {
    Long id;
    String email;
    String username;
    Role role;
    UserStatus status;
    UserNetworkStatus networkStatus;
    Boolean isVerified;
    ProfileReadDto profileReadDto;
}
