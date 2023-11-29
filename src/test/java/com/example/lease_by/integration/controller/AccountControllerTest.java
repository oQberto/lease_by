package com.example.lease_by.integration.controller;

import com.example.lease_by.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.lease_by.dto.account.ProfileCreateDto.Fields.avatar;
import static com.example.lease_by.dto.account.UserCreateDto.Fields.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@RequiredArgsConstructor
@WithMockUser(
        username = "test@gmail.com",
        password = "testPassword",
        authorities = {
                "ADMIN",
                "USER"
        }
)
class AccountControllerTest extends IntegrationTestBase {
    private static final Long EXISTING_USER_ID = 1L;
    private static final Long NOT_EXISTING_USER_ID = Long.MAX_VALUE;
    private final MockMvc mockMvc;

    @Test
    void registerUser() throws Exception {
        mockMvc.perform(post("/accounts/register")
                        .param(email, "testMail@gmail.com")
                        .param(username, "testUname")
                        .param(password, "testPassword")
                        .param(role, "USER")
                        .with(csrf()))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/cities")
                );
    }

    @Test
    void getUserAccount_whenUserIdExists_shouldReturnHttpStatus200() throws Exception {
        mockMvc.perform(get("/accounts/{username}", "username1"))
                .andExpectAll(
                        status().isOk(),
                        view().name("user/profile/account"),
                        model().attributeExists("user")
                );
    }

    @Test
    void getUserAccount_whenUserIdDoesNotExist_shouldReturnHttpStatus500() throws Exception {
        mockMvc.perform(get("/accounts/{userId}", NOT_EXISTING_USER_ID))
                .andExpectAll(
                        status().is5xxServerError(),
                        view().name("error/500.html"),
                        model().attributeExists("error")
                );
    }

    @Test
    @WithAnonymousUser
    void getUserAccount_whenUserDoesNotAuthenticated_shouldReturnHttpStatus302() throws Exception {
        mockMvc.perform(get("/accounts/{userId}", EXISTING_USER_ID))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("http://localhost/login")
                );
    }

    @Test
    void getUserProfile_whenUserIdExists_shouldReturnHttpStatus200() throws Exception {
        mockMvc.perform(get("/accounts/profile/{userId}", EXISTING_USER_ID))
                .andExpectAll(
                        status().isOk(),
                        view().name("user/profile/accountProfile"),
                        model().attributeExists("profile")
                );
    }

    @Test
    void getUserProfile_whenUserIdDoesNotExist_shouldReturnHttpStatus500() throws Exception {
        mockMvc.perform(get("/accounts/profile/{userId}", NOT_EXISTING_USER_ID))
                .andExpectAll(
                        status().is5xxServerError(),
                        view().name("error/500.html"),
                        model().attributeExists("error")
                );
    }

    @Test
    @WithAnonymousUser
    void getUserProfile_whenUserDoesNotAuthenticated_shouldReturnHttpStatus302() throws Exception {
        mockMvc.perform(get("/accounts/profile/{userId}", EXISTING_USER_ID))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("http://localhost/login")
                );
    }

    @Test
    void updateUser_whenUserIdDoesNotExist_shouldReturnHttpStatus500() throws Exception {
        mockMvc.perform(post("/accounts/{id}/update", NOT_EXISTING_USER_ID)
                        .param(email, "email@gmail.com")
                        .with(csrf()))
                .andExpectAll(
                        status().is5xxServerError(),
                        view().name("error/500.html"),
                        model().attributeExists("error")
                );
    }

    @Test
    @WithAnonymousUser
    void updateUser_whenUserDoesNotAuthenticated_shouldReturnHttpStatus302() throws Exception {
        mockMvc.perform(post("/accounts/{id}/update", EXISTING_USER_ID)
                        .param(email, "emailNew@gmail.com")
                        .with(csrf()))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("http://localhost/login")
                );
    }

    @Test
    void updateUserProfile_whenUserIdExists_shouldReturnHttpStatus302() throws Exception {
        mockMvc.perform(post("/accounts/profile/{id}/update", EXISTING_USER_ID)
                        .param(avatar, "newAvatar")
                        .with(csrf()))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlTemplate("/accounts/profile/{id}", EXISTING_USER_ID)
                );
    }

    @Test
    void updateUserProfile_whenUserIdDoesNotExist_shouldReturnHttpStatus500() throws Exception {
        mockMvc.perform(post("/accounts/profile/{id}/update", NOT_EXISTING_USER_ID)
                        .param(email, "emailNew@gmail.com")
                        .with(csrf()))
                .andExpectAll(
                        status().is5xxServerError(),
                        view().name("error/500.html"),
                        model().attributeExists("error")
                );
    }

    @Test
    @WithAnonymousUser
    void updateProfile_whenUserDoesNotAuthenticated_shouldReturnHttpStatus302() throws Exception {
        mockMvc.perform(post("/accounts/profile/{id}/update", EXISTING_USER_ID)
                        .param(email, "emailNew@gmail.com")
                        .with(csrf()))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("http://localhost/login")
                );
    }
}