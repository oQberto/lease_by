package com.example.lease_by.dto.security;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Value
@EqualsAndHashCode(callSuper = true)
public class SecurityCustomUser extends User {
    String email;

    public SecurityCustomUser(String email, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.email = email;
    }
}
