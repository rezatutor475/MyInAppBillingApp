package com.myinappbilling.email.util;

import android.text.TextUtils;
import android.util.Patterns;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Utility class for common email-related functions.
 */
public class EmailUtils {

    private EmailUtils() {
        // Private constructor to prevent instantiation
    }

    /**
     * Validates an email address format.
     *
     * @param email The email address to validate.
     * @return true if valid, false otherwise.
     */
    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * Formats the current date to a readable string.
     *
     * @return Formatted date string.
     */
    public static String getCurrentFormattedDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }

    /**
     * Formats a given date into a readable string.
     *
     * @param date The date to format.
     * @return Formatted date string.
     */
    public static String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(date);
    }

    /**
     * Parses a date string into a Date object.
     *
     * @param dateString The date string to parse.
     * @return Date object, or null if parsing fails.
     */
    public static Date parseDate(String dateString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            return sdf.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Trims and sanitizes an email subject line.
     *
     * @param subject The subject to sanitize.
     * @return Cleaned subject.
     */
    public static String sanitizeSubject(String subject) {
        if (subject == null) {
            return "";
        }
        return subject.trim().replaceAll("\\s+", " ");
    }

    /**
     * Trims and sanitizes an email body.
     *
     * @param body The body to sanitize.
     * @return Cleaned body.
     */
    public static String sanitizeBody(String body) {
        if (body == null) {
            return "";
        }
        return body.trim().replaceAll("\\s+", " ");
    }

    /**
     * Checks if the provided text is empty after trimming.
     *
     * @param text The text to check.
     * @return true if empty or null, false otherwise.
     */
    public static boolean isEmpty(String text) {
        return text == null || text.trim().isEmpty();
    }
}
