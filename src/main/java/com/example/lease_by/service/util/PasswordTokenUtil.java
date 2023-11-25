package com.example.lease_by.service.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.UUID;

@UtilityClass
public class PasswordTokenUtil {

    public static String createToken() {
        return UUID.randomUUID().toString();
    }

    public static LocalDate createExpireDate() {
        return LocalDate.now().plusDays(1);
    }
}
