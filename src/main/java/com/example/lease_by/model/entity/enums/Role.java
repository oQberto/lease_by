package com.example.lease_by.model.entity.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN,
    LANDLORD,
    USER,;


    @Override
    public String getAuthority() {
        return name();
    }
}
