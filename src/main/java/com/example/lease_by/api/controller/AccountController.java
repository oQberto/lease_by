package com.example.lease_by.api.controller;

import com.example.lease_by.api.controller.exception.VerificationTokenException;
import com.example.lease_by.api.controller.util.PageName.User;
import com.example.lease_by.dto.account.*;
import com.example.lease_by.listener.event.RegistrationCompleteEvent;
import com.example.lease_by.model.entity.enums.Role;
import com.example.lease_by.service.EmailService;
import com.example.lease_by.service.ProfileService;
import com.example.lease_by.service.UserService;
import com.example.lease_by.service.VerificationTokenService;
import com.example.lease_by.service.exception.PasswordUpdateException;
import com.example.lease_by.vaidation.VerificationTokenValidation;
import com.example.lease_by.vaidation.group.CreateAction;
import com.example.lease_by.vaidation.group.UpdateAction;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
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
import static com.example.lease_by.vaidation.enums.VerificationTokenStatus.EXPIRED;
import static com.example.lease_by.vaidation.enums.VerificationTokenStatus.INVALID;

@Controller
@RequiredArgsConstructor
@RequestMapping(ACCOUNTS)
public class AccountController {
    private final UserService userService;
    private final EmailService emailService;
    private final ProfileService profileService;
    private final VerificationTokenService verificationTokenService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final VerificationTokenValidation verificationTokenValidation;

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

        UserReadDto registeredUser = userService.createUser(userCreateDto);
        applicationEventPublisher.publishEvent(
                new RegistrationCompleteEvent(registeredUser)
        );

        return "redirect:" + CITIES;
    }

    @GetMapping("/verify-user")
    public String verifyUser(@RequestParam("token") String token) {
        isTokenValid(token);

        userService.verifyUser(token);

        return "redirect:/cities";
    }

    @GetMapping("/request-verification")
    public String requestVerificationCode(@RequestParam("email") String email) {
        UserReadDto userReadDto = userService.getUserByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User with email: " + email + " not found!"));

        verificationTokenService.createToken(userReadDto);
        emailService.sendUserVerificationMessage(email);

        return "redirect:/accounts/" + userReadDto.getUsername();
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
        var verificationTokenStatus = verificationTokenValidation.validateVerificationToken(token);

        if (verificationTokenStatus.equals(INVALID) || verificationTokenStatus.equals(EXPIRED)) {
            throw new VerificationTokenException("You have an invalid or expired token. Request another token.");
        }
    }

    @PostMapping("/{id}/update-password")
    @PreAuthorize("isAuthenticated()")
    public String updatePassword(@PathVariable("id") Long id,
                                 @ModelAttribute PasswordEditDto passwordEditDto) {
        return userService.updatePassword(id, passwordEditDto)
                .map(it -> {
                    var username = userService
                            .getUserById(id)
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
                            .getUsername();

                    return "redirect:/accounts/" + username;
                })
                .orElseThrow(() -> new PasswordUpdateException("New and confirm passwords don't match or incorrect old password"));
    }

    @GetMapping(ACCOUNT_BY_USERNAME)
    @PreAuthorize("isAuthenticated()")
    public String getUserAccount(Model model,
                                 @PathVariable("username") String username,
                                 RedirectAttributes redirectAttributes) {
        return userService.getUserByUsername(username)
                .map(user -> {
                    model.addAttribute("user", user);
                    redirectAttributes.addFlashAttribute("username", username);
                    return "user/profile/account";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping(PROFILE_BY_ID)
    @PreAuthorize("isAuthenticated()")
    public String getUserProfile(Model model,
                                 @PathVariable("profileId") Long id) {
        return profileService.getProfileById(id)
                .map(profile -> {
                    model.addAttribute("profile", profile);
                    return "user/profile/accountProfile";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping(UPDATE_ACCOUNT_BY_USER_ID)
    @PreAuthorize("isAuthenticated()")
    public String updateUser(@PathVariable("id") Long id,
                             @ModelAttribute("updatedUser") @Validated({Default.class, UpdateAction.class}) UserEditDto userEditDto) {
        return userService.updateUser(id, userEditDto)
                .map(it -> {
                    var username = userService
                            .getUserById(id)
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
                            .getUsername();

                    return "redirect:/accounts/" + username;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping(UPDATE_PROFILE_BY_USER_ID)
    @PreAuthorize("isAuthenticated()")
    public String updateUserProfile(@PathVariable("id") Long id,
                                    @ModelAttribute("updatedProfile") ProfileEditDto profileEditDto) {
        return profileService.updateProfile(id, profileEditDto)
                .map(it -> "redirect:/accounts/profile/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
