package com.example.lease_by.api.controller.util;

public interface PageName {
    String RENTAL = "rental/";
    String CITY = "city/";
    String USER = "user/";

    interface Rental {
        String POST_RENTAL = RENTAL + "postRental";
        String RENTAL_INFO = RENTAL + "rentalInfo";
        String RENTALS = RENTAL + "rentals";
    }

    interface City {
        String CITIES = CITY + "cities";
    }

    interface User {
        String ACCOUNT_INFO = USER + "accountInfo";
        String LOGIN = USER + "login";
        String FORGOT_PASSWORD = USER + "forgotPassword";
        String CHANGE_PASSWORD = USER + "changePassword";

        String PROFILE = USER + "profile/";
        interface Profile {
            String LISTINGS = PROFILE + "listings";
        }
    }
}
