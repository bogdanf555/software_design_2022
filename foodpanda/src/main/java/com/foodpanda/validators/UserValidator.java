package com.foodpanda.validators;

import com.foodpanda.model.User;

public class UserValidator {

    private static UserValidator userValidator;

    private UserValidator() {
    }

    public static UserValidator getInstance() {

        if (userValidator == null)
            userValidator = new UserValidator();

        return userValidator;
    }

    public String validateUser(User user) {
        return "";
    }

    public String validateAdmin(User user) {

        String response = this.validateUser(user);

        if (response.isEmpty()) {
            if(!user.getRole().equals("admin")) {
                return "user is not admin";
            }

            return "";
        }

        return response;
    }
}
