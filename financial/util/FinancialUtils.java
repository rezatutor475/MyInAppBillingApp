package com.myinappbilling.financial.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * FinancialUtils provides utility methods for financial operations.
 */
public class FinancialUtils {

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * Generates a unique transaction ID using UUID.
     *
     * @return A unique transaction ID.
     */
    public static String generateTransactionId() {
        return UUID.randomUUID().toString();
    }

    /**
     * Gets the current timestamp formatted as a string.
     *
     * @return The current timestamp.
     */
    public static String getCurrentTimestamp() {
        return new SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(new Date());
    }

    /**
     * Formats a given date object into a readable string.
     *
     * @param date The date to format.
     * @return A formatted date string.
     */
    public static String formatDate(Date date) {
        if (date == null) return "";
        return new SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(date);
    }

    /**
     * Parses a date string back into a Date object.
     *
     * @param dateString The string to parse.
     * @return A Date object or null if parsing fails.
     */
    public static Date parseDate(String dateString) {
        try {
            return new SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Validates if the amount is a positive value.
     *
     * @param amount The amount to validate.
     * @return True if valid, false otherwise.
     */
    public static boolean isValidAmount(double amount) {
        return amount > 0;
    }

    /**
     * Converts a string to double with fallback in case of error.
     *
     * @param amountStr The amount string to convert.
     * @return Parsed double or 0.0 if invalid.
     */
    public static double parseAmount(String amountStr) {
        try {
            return Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    /**
     * Calculates a future date based on days added to the current date.
     *
     * @param days Number of days to add.
     * @return A formatted future date string.
     */
    public static String getFutureDate(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return formatDate(calendar.getTime());
    }

    /**
     * Rounds a double to two decimal places.
     *
     * @param value The double value.
     * @return Rounded double.
     */
    public static double roundToTwoDecimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    /**
     * Checks if a date is in the past.
     *
     * @param date The date to check.
     * @return True if the date is before today, false otherwise.
     */
    public static boolean isPastDate(Date date) {
        return date != null && date.before(new Date());
    }
}
