package com.example.login_service.DTO;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class Validation {

    private static final String NUMBER_REGEX = "^[6-9]\\d{9}$";

    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";

    private static final Pattern numberPattern = Pattern.compile(NUMBER_REGEX);
    private static final Pattern passwordPattern = Pattern.compile(PASSWORD_REGEX);

    public boolean isValidNumber(long number) {
        String numberStr = String.valueOf(number);
        return numberPattern.matcher(numberStr).matches();
    }

    public boolean isValidPassword(String password) {
        if (password == null)
            return false;
        return passwordPattern.matcher(password).matches();
    }

    public boolean isValidRole(String role) {
        if (role == null)
            return false;
        if (role.equalsIgnoreCase("Traveler") || role.equalsIgnoreCase("Hotel manager")
                || role.equalsIgnoreCase("Travel agent") || role.equalsIgnoreCase("Admin")) {
            return true;
        }
        return false;
    }
}
