package com.example.lease_by.api.controller.util;

public interface UrlName {

    interface AccountController {
        String ACCOUNTS = "/accounts";
        String REGISTER = "/register";
        String ACCOUNT_BY_USERNAME = "/{username}";
        String FORGOT_PASSWORD = "/forgot-password";
        String PROFILE_BY_ID = "/profile/{profileId}";
        String CHANGE_PASSWORD = "/user/change-password";
        String UPDATE_ACCOUNT_BY_USER_ID = "/{id}/update";
        String UPDATE_PROFILE_BY_USER_ID = "/profile/{id}/update";
    }

    interface CityController {
        String CITIES = "/cities";
    }

    interface LoginController {
        String LOGIN = "/login";
    }

    interface RentalController {
        String RENTALS = "/rentals";
        String IMAGE_BY_PATH = "/image/{image}";
        String RENTALS_BY_USERNAME = "/account/{username}";
        String RENTAL_DETAILS_BY_RENTAL_ID = "/rental-details/{id}";
        String RENTAL = "/rental";
        String POST_RENTAL = "/post-rental";
        String RENTALS_BY_CITY_NAME = "/{cityName}";
        String RENTALS_BY_ADDRESS = "/address/{address}";
        String FILTERED_RENTALS = RENTALS_BY_CITY_NAME + "/filtered";
    }
}
