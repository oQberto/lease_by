package com.example.lease_by.config.handler;

import com.example.lease_by.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.example.lease_by.api.controller.util.UrlName.CityController.CITIES;
import static com.example.lease_by.model.entity.enums.UserNetworkStatus.ONLINE;

@Component
@RequiredArgsConstructor
public class UserLoginHandler implements AuthenticationSuccessHandler {
    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        var username = authentication.getName();

        userService.updateUserNetworkStatus(username, ONLINE);

        response.sendRedirect(CITIES);
    }
}
