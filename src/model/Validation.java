package model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    //public static final String BLUE = "\033[0;34m";
    public static final String LOGIN_NAME_REGEX = "^[a-zA-Z0-9]{8,16}$";
    public static final String PASSWORD_REGEX = "^[a-zA-Z0-9]{8,16}$";
    public static final String PHONE_NUMBER_REGEX = "^0[35789][0-9]{8}$";
    public static final String DATE_REGEX = "^\\d{2}/\\d{2}/\\d{4}$";
    public static final String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

    public static boolean validateString(String string, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }
}
