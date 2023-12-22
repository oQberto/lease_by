package com.example.lease_by.config.handler;

import com.example.lease_by.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import static com.example.lease_by.model.entity.enums.UserNetworkStatus.OFFLINE;

@Component
@RequiredArgsConstructor
public class UserLogoutHandler implements LogoutHandler {
    private final UserService userService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        var username = authentication.getName();

        userService.updateUserNetworkStatus(username, OFFLINE);
    }
}
