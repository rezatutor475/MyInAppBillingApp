package com.myinappbilling.security.verifier;

import java.util.regex.Pattern;
import java.util.Objects;

/**
 * Verifies the validity of user credentials.
 */
public class CredentialVerifier {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$");
    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^\\+?[0-9]{7,15}$");

    /**
     * Validates an email address.
     *
     * @param email the email to validate
     * @return true if the email is valid; false otherwise
     */
    public boolean validateEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Validates a password.
     * Requires at least one digit, one lowercase, one uppercase letter, and a minimum of 8 characters.
     *
     * @param password the password to validate
     * @return true if the password is strong; false otherwise
     */
    public boolean validatePassword(String password) {
        return password != null && PASSWORD_PATTERN.matcher(password).matches();
    }

    /**
     * Validates a phone number.
     * Accepts optional "+" and 7-15 digits.
     *
     * @param phone the phone number to validate
     * @return true if valid; false otherwise
     */
    public boolean validatePhoneNumber(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone).matches();
    }

    /**
     * Validates both email and password.
     *
     * @param email the user's email
     * @param password the user's password
     * @return true if both credentials are valid; false otherwise
     */
    public boolean validateCredentials(String email, String password) {
        return validateEmail(email) && validatePassword(password);
    }

    /**
     * Sanitizes an input string by trimming whitespace and removing dangerous characters.
     *
     * @param input the string to sanitize
     * @return a sanitized version of the string
     */
    public String sanitizeInput(String input) {
        if (input == null) return null;
        return input.trim().replaceAll("[<>\\\\\"']", "");
    }

    /**
     * Compares provided credentials against stored credentials.
     *
     * @param inputEmail input email
     * @param inputPassword input password
     * @param storedEmail stored email
     * @param storedPassword stored password
     * @return true if both email and password match; false otherwise
     */
    public boolean compareCredentials(String inputEmail, String inputPassword,
                                      String storedEmail, String storedPassword) {
        return Objects.equals(inputEmail, storedEmail) && Objects.equals(inputPassword, storedPassword);
    }

    /**
     * Checks if a string is null or empty.
     *
     * @param input the input string
     * @return true if null or empty; false otherwise
     */
    public boolean isNullOrEmpty(String input) {
        return input == null || input.trim().isEmpty();
    }

    /**
     * Masks a password for secure display or logging.
     *
     * @param password the password to mask
     * @return masked string with asterisks
     */
    public String maskPassword(String password) {
        if (password == null) return null;
        return "*".repeat(password.length());
    }
}
