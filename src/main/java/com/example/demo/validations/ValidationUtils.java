package com.example.demo.validations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtils {
    public static boolean isValidEmail(String email) {
        if (email == null)
            return false;
        Pattern pattern = Pattern.compile("^([\\w\\.\\-]+)@([\\w\\-]+)((\\.(\\w){2,3})+)$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidName(String name, int minLength, int maxLength) {
        if (name == null)
            return false;
        return name.length() >= minLength && name.length() <= maxLength;
    }

    public static boolean isValidPassword(String password, int minLength, int maxLength) {
        if (password == null)
            return false;
        String regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{" + minLength + "," + maxLength + "}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
