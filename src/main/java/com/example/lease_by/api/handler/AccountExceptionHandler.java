package com.example.lease_by.api.handler;

import com.example.lease_by.service.exception.PasswordUpdateException;
import com.example.lease_by.service.exception.UserCredentialsUpdateException;
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

    @ExceptionHandler(UserCredentialsUpdateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String userCredentialsUpdateException(Model model,
                                                 UserCredentialsUpdateException exception) {
        log.info("Failed to update user", exception);

        model.addAttribute("error", exception.getMessage());

        return "error/409.html";
    }

    @ExceptionHandler(PasswordUpdateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String passwordUpdateException(Model model,
                                          PasswordUpdateException exception) {
        log.info("Couldn't update password: ", exception);

        model.addAttribute("error", exception.getMessage());

        return "error/400.html";
    }
}
