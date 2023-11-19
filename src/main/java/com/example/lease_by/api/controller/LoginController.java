package com.example.lease_by.api.controller;

import com.example.lease_by.dto.account.LoginDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import static com.example.lease_by.api.controller.util.UrlName.LoginController.LOGIN;

@Controller
public class LoginController {

    @GetMapping(LOGIN)
    public String loginPage() {
        return "/user/login";
    }

    @PostMapping(LOGIN)
    public String login(Model model,
                        @ModelAttribute("login") LoginDto loginDto) {
        return "redirect:/login";
    }
}
