package com.example.lease_by.api.handler;

import com.example.lease_by.api.handler.annotation.Handler;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.example.lease_by.api.handler.util.ErrorPage.ERROR_PAGE_404;

@Slf4j
@Handler
public class CommonExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String entityNotFoundException(Model model,
                                          EntityNotFoundException exception) {
        log.info("Chat not found! Cause: wrong sender and recipient ids!");

        model.addAttribute("error", exception);

        return ERROR_PAGE_404;
    }
}
