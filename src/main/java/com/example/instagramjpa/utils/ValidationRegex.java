package com.example.instagramjpa.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationRegex {
    public static boolean isRegexIdString(String target) {
        String regex = "^[0-9]*$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }
    public static boolean isRegexId(String target) {
        String regex = "[a-z0-9_|.]{5,20}$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }
    public static boolean isRegexPhoneNumber(String target){
        String regex="^\\d{3}-\\d{3,4}-\\d{4}$";
        Pattern pattern=Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }
}

