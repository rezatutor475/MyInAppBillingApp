package com.myinappbilling.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * DateUtils is a utility class that provides helper methods for date formatting,
 * parsing, and manipulation.
 */
public class DateUtils {

    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    private static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * Returns the current date formatted as a string.
     *
     * @return The current date in default format.
     */
    public static String getCurrentDate() {
        return formatDate(new Date(), DEFAULT_DATE_FORMAT);
    }

    /**
     * Returns the current date and time formatted as a string.
     *
     * @return The current date and time in default datetime format.
     */
    public static String getCurrentDateTime() {
        return formatDate(new Date(), DEFAULT_DATETIME_FORMAT);
    }

    /**
     * Formats a Date object to a string using the given format.
     *
     * @param date The Date object to format.
     * @param format The date format string.
     * @return A formatted date string.
     */
    public static String formatDate(Date date, String format) {
        if (date == null || format == null || format.isEmpty()) return null;
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        return sdf.format(date);
    }

    /**
     * Parses a date string to a Date object using the given format.
     *
     * @param dateString The date string to parse.
     * @param format The date format string.
     * @return A Date object or null if parsing fails.
     */
    public static Date parseDate(String dateString, String format) {
        if (dateString == null || format == null || format.isEmpty()) return null;
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            Logger.e("DateUtils", "Error parsing date: " + dateString, e);
            return null;
        }
    }

    /**
     * Adds or subtracts days to a given date.
     *
     * @param date The base date.
     * @param days Number of days to add (use negative to subtract).
     * @return The new date.
     */
    public static Date addDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return calendar.getTime();
    }

    /**
     * Checks if two dates are on the same day.
     *
     * @param date1 The first date.
     * @param date2 The second date.
     * @return True if both dates are on the same day.
     */
    public static boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) return false;
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * Returns the number of days between two dates.
     *
     * @param startDate The start date.
     * @param endDate The end date.
     * @return Number of days between the dates.
     */
    public static long getDaysBetween(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) return 0;
        long diffInMillis = endDate.getTime() - startDate.getTime();
        return diffInMillis / (24 * 60 * 60 * 1000);
    }

    /**
     * Gets the start of the day for a given date.
     *
     * @param date The date.
     * @return A Date representing the start of the day.
     */
    public static Date getStartOfDay(Date date) {
        if (date == null) return null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * Gets the end of the day for a given date.
     *
     * @param date The date.
     * @return A Date representing the end of the day.
     */
    public static Date getEndOfDay(Date date) {
        if (date == null) return null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }
} 
