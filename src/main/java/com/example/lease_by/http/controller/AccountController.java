package com.example.lease_by.http.controller;

import com.example.lease_by.dto.ProfileCreateDto;
import com.example.lease_by.dto.UserCreateDto;
import com.example.lease_by.model.entity.enums.Role;
import com.example.lease_by.service.ProfileService;
import com.example.lease_by.service.UserService;
import com.example.lease_by.vaidation.group.CreateAction;
import com.example.lease_by.vaidation.group.UpdateAction;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {
    private final UserService userService;
    private final ProfileService profileService;

    @GetMapping("/registration")
    public String registration(Model model,
                               @ModelAttribute("user") UserCreateDto userCreateDto) {
        model.addAttribute("user", userCreateDto);
        return "user/registration";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") @Validated({Default.class, CreateAction.class}) UserCreateDto userCreateDto,
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

    @GetMapping("/{id}")
    public String getUserAccount(Model model,
                                 @PathVariable("id") Long id) {
        return userService.getUserById(id)
                .map(user -> {
                    model.addAttribute("user", user);
                    return "user/accountInfo";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/profile/{userId}")
    @PreAuthorize("isAuthenticated()")
    public String getUserProfile(Model model,
                                 @PathVariable("userId") Long id) {
        return profileService.getProfileByUserId(id)
                .map(profile -> {
                    model.addAttribute("profile", profile);
                    return "user/profile";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/update")
    @PreAuthorize("isAuthenticated()")
    public String updateUser(@PathVariable("id") Long id,
                             @ModelAttribute("updatedUser") @Validated({Default.class, UpdateAction.class}) UserCreateDto updatedUser) {
        return userService.updateUser(id, updatedUser)
                .map(it -> "redirect:/accounts/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/profile/{id}/update")
    @PreAuthorize("isAuthenticated()")
    public String updateUserProfile(@PathVariable("id") Long id,
                                    @ModelAttribute("updatedProfile") ProfileCreateDto updatedProfile) {
        return profileService.updateProfile(id, updatedProfile)
                .map(it -> "redirect:/accounts/profile/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
