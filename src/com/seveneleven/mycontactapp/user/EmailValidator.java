package com.seveneleven.mycontactapp.user;

import java.util.regex.Pattern;

public final class EmailValidator {
    private static final String emailPattern = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$";

    private EmailValidator() {}

    public static void validate(String email) {
        if (email == null || email.trim().isEmpty())
            throw new IllegalArgumentException("Email required");
        if (!Pattern.matches(emailPattern,email.trim()))
            throw new IllegalArgumentException("Invalid email format");
    }
}



