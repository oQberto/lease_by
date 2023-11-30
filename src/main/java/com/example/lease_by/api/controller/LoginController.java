package com.example.lease_by.api.controller;

import com.example.lease_by.api.controller.exception.LoginException;
import com.example.lease_by.dto.account.LoginDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import static com.example.lease_by.api.controller.util.UrlName.LoginController.LOGIN;

@Controller
public class LoginController {

    @GetMapping(LOGIN)
    @PreAuthorize("!isAuthenticated()")
    public String loginPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!authentication.isAuthenticated()) {
            throw new LoginException("User has already logged in");
        }

        return "/user/login";
    }

    @PostMapping(LOGIN)
    public String login(@ModelAttribute("login") LoginDto loginDto) {
        return "redirect:/login";
    }
}
