package com.myinappbilling.email.validator;

import java.util.regex.Pattern;

/**
 * Utility class for validating email addresses and related logic.
 */
public class EmailValidator {

    private static final String EMAIL_REGEX =
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);

    /**
     * Validates the given email address.
     *
     * @param email the email address to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValid(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Extracts the domain part of the email address.
     *
     * @param email the email address
     * @return the domain if valid, null otherwise
     */
    public static String getDomain(String email) {
        if (isValid(email)) {
            int atIndex = email.lastIndexOf("@");
            return email.substring(atIndex + 1);
        }
        return null;
    }

    /**
     * Checks if the email belongs to a specified domain.
     *
     * @param email  the email address
     * @param domain the expected domain
     * @return true if it matches, false otherwise
     */
    public static boolean hasDomain(String email, String domain) {
        String emailDomain = getDomain(email);
        return domain != null && domain.equalsIgnoreCase(emailDomain);
    }

    /**
     * Checks if the email uses a free domain provider (e.g. gmail, yahoo).
     *
     * @param email the email address
     * @return true if it's a free provider, false otherwise
     */
    public static boolean isFreeEmailProvider(String email) {
        String domain = getDomain(email);
        return domain != null && (
                domain.equalsIgnoreCase("gmail.com") ||
                domain.equalsIgnoreCase("yahoo.com") ||
                domain.equalsIgnoreCase("hotmail.com") ||
                domain.equalsIgnoreCase("outlook.com")
        );
    }

    /**
     * Masks the user part of the email address for privacy.
     *
     * @param email the email address
     * @return masked email, or null if invalid
     */
    public static String maskEmail(String email) {
        if (!isValid(email)) {
            return null;
        }
        int atIndex = email.indexOf("@");
        String username = email.substring(0, atIndex);
        String domain = email.substring(atIndex);
        if (username.length() <= 2) {
            return "**" + domain;
        }
        return username.substring(0, 2) + "***" + domain;
    }
}
