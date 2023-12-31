package com.example.lease_by.dto.account;

import com.example.lease_by.model.entity.enums.Role;
import com.example.lease_by.vaidation.group.CreateAction;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Value
@Builder
@FieldNameConstants
public class UserCreateDto implements UserDetails {


    @Email
    String email;

    @NotEmpty(groups = CreateAction.class)
    String username;

    @NotBlank(groups = CreateAction.class)
    String password;

    Role role;

    Boolean isVerified = false;

    ProfileCreateDto profileCreateDto;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
