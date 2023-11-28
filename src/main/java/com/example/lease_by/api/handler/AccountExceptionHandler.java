package com.example.lease_by.api.handler;

import com.example.lease_by.service.exception.UserUpdateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice(basePackages = "com.example.lease_by.api.controller")
public class AccountExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserUpdateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String userCredentialsUpdateException(Model model,
                                                 UserUpdateException userUpdateException) {
        log.info("Failed to update user", userUpdateException);

        model.addAttribute("error", userUpdateException.getMessage());

        return "error/409.html";
    }
}
