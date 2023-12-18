package com.example.lease_by.integration.controller;

import com.example.lease_by.integration.IntegrationTestBase;
import com.example.lease_by.model.entity.enums.Role;
import com.example.lease_by.model.entity.enums.UserStatus;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.lease_by.model.entity.User.Fields.role;
import static com.example.lease_by.model.entity.User.Fields.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RequiredArgsConstructor
@AutoConfigureMockMvc
@WithMockUser(
        username = "admin@gmail.com",
        password = "adminPassword",
        roles = "ADMIN"
)
class AdminControllerTest extends IntegrationTestBase {
    private static final Long EXISTING_USER_ID = 1L;
    private static final Long NOT_EXISTING_USER_ID = Long.MAX_VALUE;
    private final MockMvc mockMvc;

    @Test
    void updateUserStatus_whenUserIdExists_shouldReturnHttpStatus302() throws Exception {
        mockMvc.perform(post("/admin/user/{id}/update-status", EXISTING_USER_ID)
                        .param(status, UserStatus.BLOCKED.name())
                        .with(csrf()))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/admin")
                );
    }

    @Test
    void updateUserStatus_whenUserIdDoesNotExist_shouldReturnHttpStatus400() throws Exception {
        mockMvc.perform(post("/admin/user/{id}/update-status", NOT_EXISTING_USER_ID)
                        .param(status, UserStatus.BLOCKED.name())
                        .with(csrf()))
                .andExpectAll(
                        status().is4xxClientError(),
                        view().name("error/4xx/400.html"),
                        model().attributeExists("error")
                );
    }

    @Test
    void updateUserRole_whenUserIdExists_shouldReturnHttpStatus302() throws Exception {
        mockMvc.perform(post("/admin/user/{id}/update-role", EXISTING_USER_ID)
                        .param(role, Role.USER.name())
                        .with(csrf()))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/admin")
                );
    }

    @Test
    void updateUserRole_whenUserIdDoesNotExist_shouldReturnHttpStatus400() throws Exception {
        mockMvc.perform(post("/admin/user/{id}/update-role", NOT_EXISTING_USER_ID)
                        .param(role, Role.USER.name())
                        .with(csrf()))
                .andExpectAll(
                        status().is4xxClientError(),
                        view().name("error/4xx/400.html"),
                        model().attributeExists("error")
                );
    }

    @Test
    void deleteUser_whenUserIdExists_shouldReturnHttpsStatus204() throws Exception {
        mockMvc.perform(post("/admin/user/{id}/delete", EXISTING_USER_ID)
                        .with(csrf()))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        redirectedUrl("/admin")
                );
    }
}