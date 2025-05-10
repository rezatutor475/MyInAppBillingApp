package com.myinappbilling.security.util;

import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.regex.Pattern;

/**
 * Utility class for common security-related operations.
 */
public class SecurityUtils {

    private static final String TAG = "SecurityUtils";

    /**
     * Hashes input using SHA-256.
     *
     * @param input the string to hash
     * @return the hashed string in Base64, or null if an error occurs
     */
    public static String hashSHA256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());
            return Base64.encodeToString(hash, Base64.NO_WRAP);
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "SHA-256 algorithm not found", e);
            return null;
        }
    }

    /**
     * Validates if a given string is a valid Base64 encoded string.
     *
     * @param input the string to validate
     * @return true if valid Base64, false otherwise
     */
    public static boolean isValidBase64(String input) {
        try {
            Base64.decode(input, Base64.DEFAULT);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Validates a password based on security standards.
     * Requires at least 8 characters, one digit, one lowercase, one uppercase.
     *
     * @param password the password to validate
     * @return true if the password is secure, false otherwise
     */
    public static boolean isPasswordSecure(String password) {
        String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";
        return Pattern.matches(pattern, password);
    }

    /**
     * Obfuscates sensitive data for display.
     * Example: 1234567890 -> ******7890
     *
     * @param data the string to obfuscate
     * @return obfuscated string
     */
    public static String obfuscate(String data) {
        if (data == null || data.length() <= 4) return data;
        int visibleChars = 4;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length() - visibleChars; i++) {
            sb.append("*");
        }
        sb.append(data.substring(data.length() - visibleChars));
        return sb.toString();
    }

    /**
     * Generates a secure random token.
     *
     * @param length the desired length of the token
     * @return a secure random token as a Base64 string
     */
    public static String generateSecureToken(int length) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] tokenBytes = new byte[length];
        secureRandom.nextBytes(tokenBytes);
        return Base64.encodeToString(tokenBytes, Base64.NO_WRAP);
    }

    /**
     * Validates an email address format.
     *
     * @param email the email to validate
     * @return true if the email is valid, false otherwise
     */
    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";
        return Pattern.matches(emailPattern, email);
    }
}
