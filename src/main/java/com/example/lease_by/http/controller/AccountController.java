package com.example.lease_by.http.controller;

import com.example.lease_by.dto.UserCreateDto;
import com.example.lease_by.dto.UserReadDto;
import com.example.lease_by.model.entity.enums.Role;
import com.example.lease_by.service.ProfileService;
import com.example.lease_by.service.UserService;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
    private final UserService userService;
    private final ProfileService profileService;

    @GetMapping("/registration")
    public String registration(Model model,
                               @ModelAttribute("user") UserCreateDto userCreateDto) {
        model.addAttribute("user", userCreateDto);
        return "user/registration";
    }

    @PostMapping("/create")
    public String registerUser(@ModelAttribute("user") @Validated({Default.class}) UserCreateDto userCreateDto,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("user", userCreateDto);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/account/registration";
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(userCreateDto, null, List.of(Role.values()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        userService.createUser(userCreateDto);
        return "redirect:/cities";
    }

    @GetMapping
    public String getUserAccount(Model model,
                                 @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        Optional<UserReadDto> userByEmail = userService.getUserByEmail(email);
        model.addAttribute("user", userByEmail.get());
        return "user/account";
    }
}
