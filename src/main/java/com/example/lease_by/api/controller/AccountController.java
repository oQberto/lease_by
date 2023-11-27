package com.example.lease_by.api.controller;

import com.example.lease_by.api.controller.exception.PasswordTokenException;
import com.example.lease_by.api.controller.util.PageName.User;
import com.example.lease_by.dto.account.PasswordDto;
import com.example.lease_by.dto.account.ProfileCreateDto;
import com.example.lease_by.dto.account.UserCreateDto;
import com.example.lease_by.model.entity.enums.Role;
import com.example.lease_by.service.ProfileService;
import com.example.lease_by.service.UserService;
import com.example.lease_by.vaidation.PasswordTokenValidation;
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

import static com.example.lease_by.api.controller.util.UrlName.AccountController.*;
import static com.example.lease_by.api.controller.util.UrlName.CityController.CITIES;
import static com.example.lease_by.api.controller.util.UrlName.LoginController.LOGIN;
import static com.example.lease_by.vaidation.enums.PasswordTokenVerification.EXPIRED;
import static com.example.lease_by.vaidation.enums.PasswordTokenVerification.INVALID;

@Controller
@RequiredArgsConstructor
@RequestMapping(ACCOUNTS)
public class AccountController {
    private final UserService userService;
    private final ProfileService profileService;
    private final PasswordTokenValidation passwordTokenValidation;

    @PostMapping(REGISTER)
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
        return "redirect:" + CITIES;
    }

    @GetMapping(FORGOT_PASSWORD)
    public String forgotPassword() {
        return User.FORGOT_PASSWORD;
    }

    @GetMapping(CHANGE_PASSWORD)
    public String showChangePassword(Model model,
                                     @RequestParam("token") String token,
                                     @RequestParam("username") String username) {
        isTokenValid(token);

        model.addAttribute("token", token);
        model.addAttribute("username", username);

        return User.CHANGE_PASSWORD;

    }

    @PostMapping(CHANGE_PASSWORD)
    public String savePassword(@ModelAttribute PasswordDto passwordDto) {
        isTokenValid(passwordDto.getToken());

        Authentication authentication = new UsernamePasswordAuthenticationToken(passwordDto, null, List.of(Role.values()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        userService.saveUserPassword(passwordDto);
        return "redirect:" + LOGIN;
    }

    private void isTokenValid(String token) {
        var passwordTokenVerification = passwordTokenValidation.validatePasswordToken(token);

        if (passwordTokenVerification.equals(INVALID) || passwordTokenVerification.equals(EXPIRED)) {
            throw new PasswordTokenException("You have an invalid or expired token. Request another token.");
        }
    }

    @GetMapping(ACCOUNT_BY_USER_ID)
    public String getUserAccount(Model model,
                                 @PathVariable("id") Long id) {
        return userService.getUserById(id)
                .map(user -> {
                    model.addAttribute("user", user);
                    return "user/accountInfo";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping(PROFILE_BY_USER_ID)
    @PreAuthorize("isAuthenticated()")
    public String getUserProfile(Model model,
                                 @PathVariable("userId") Long id) {
        return profileService.getProfileByUserId(id)
                .map(profile -> {
                    model.addAttribute("profile", profile);
                    return "user/profile/listings";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping(UPDATE_ACCOUNT_BY_USER_ID)
    @PreAuthorize("isAuthenticated()")
    public String updateUser(@PathVariable("id") Long id,
                             @ModelAttribute("updatedUser") @Validated({Default.class, UpdateAction.class}) UserCreateDto updatedUser) {
        return userService.updateUser(id, updatedUser)
                .map(it -> "redirect:/accounts/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping(UPDATE_PROFILE_BY_USER_ID)
    @PreAuthorize("isAuthenticated()")
    public String updateUserProfile(@PathVariable("id") Long id,
                                    @ModelAttribute("updatedProfile") ProfileCreateDto updatedProfile) {
        return profileService.updateProfile(id, updatedProfile)
                .map(it -> "redirect:/accounts/profile/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
