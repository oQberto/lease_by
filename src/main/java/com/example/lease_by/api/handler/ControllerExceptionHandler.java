package com.example.lease_by.api.handler;

import com.example.lease_by.api.controller.exception.LoginException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.example.lease_by.api.handler.util.ErrorPage.ERROR_PAGE_403;
import static com.example.lease_by.api.handler.util.ErrorPage.ERROR_PAGE_404;

@Slf4j
@ControllerAdvice(basePackages = "com.example.lease_by.api.controller")
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleResponseStatusException(Model model,
                                                EntityNotFoundException exception) {
        log.error("Failed to return response", exception);

        model.addAttribute("error", exception.getMessage());

        return ERROR_PAGE_404;
    }

    @ExceptionHandler(LoginException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleLoginException(Model model,
                                       LoginException exception) {
        log.error("User has already logged in", exception);

        model.addAttribute("error", exception.getMessage());

        return ERROR_PAGE_403;
    }
}
