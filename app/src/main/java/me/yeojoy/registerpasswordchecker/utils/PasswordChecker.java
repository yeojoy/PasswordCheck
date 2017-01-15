package me.yeojoy.registerpasswordchecker.utils;

import java.util.regex.Pattern;

/**
 * Created by yeojoy on 2017. 1. 16..
 */

public class PasswordChecker {
    public static boolean isPasswordMoreThanEight(CharSequence password) {
        return password.length() >= 8;
    }

    public static boolean hasCapital(CharSequence password) {
        String upperCaseMoreThanOnePattern = "[A-Z]+";
        Pattern upperCasePattern = Pattern.compile(upperCaseMoreThanOnePattern);
        return upperCasePattern.matcher(password).find();
    }

    public static boolean hasLowerCase(CharSequence password) {
        String lowerCaseMoreThanOnePattern = "[a-z]+";
        Pattern lowerCasePattern = Pattern.compile(lowerCaseMoreThanOnePattern);

        return lowerCasePattern.matcher(password).find();
    }

    public static boolean hasNumber(CharSequence password) {
        String numberMoreThanOnePattern = "[\\d]+";
        Pattern numberPattern = Pattern.compile(numberMoreThanOnePattern);

        return numberPattern.matcher(password).find();
    }
}
