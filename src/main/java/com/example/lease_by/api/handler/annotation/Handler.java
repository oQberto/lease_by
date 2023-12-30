package com.example.lease_by.api.handler.annotation;

import org.springframework.web.bind.annotation.ControllerAdvice;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ControllerAdvice(basePackages = "com.example.lease_by.api.controller")
public @interface Handler {
}
