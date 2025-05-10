package com.myinappbilling.util;

import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Logger is a utility class that provides centralized logging functionality
 * for consistent and controlled logging throughout the application.
 */
public class Logger {

    private static final String DEFAULT_TAG = "MyInAppBilling";
    private static boolean isLoggingEnabled = true;

    /**
     * Enables or disables logging globally.
     *
     * @param enabled True to enable logging, false to disable.
     */
    public static void setLoggingEnabled(boolean enabled) {
        isLoggingEnabled = enabled;
    }

    public static void d(String tag, String message) {
        if (isLoggingEnabled) {
            Log.d(tag, formatMessage(message));
        }
    }

    public static void d(String message) {
        d(DEFAULT_TAG, message);
    }

    public static void i(String tag, String message) {
        if (isLoggingEnabled) {
            Log.i(tag, formatMessage(message));
        }
    }

    public static void i(String message) {
        i(DEFAULT_TAG, message);
    }

    public static void w(String tag, String message) {
        if (isLoggingEnabled) {
            Log.w(tag, formatMessage(message));
        }
    }

    public static void w(String message) {
        w(DEFAULT_TAG, message);
    }

    public static void e(String tag, String message) {
        if (isLoggingEnabled) {
            Log.e(tag, formatMessage(message));
        }
    }

    public static void e(String message) {
        e(DEFAULT_TAG, message);
    }

    public static void e(String tag, String message, Throwable throwable) {
        if (isLoggingEnabled) {
            Log.e(tag, formatMessage(message), throwable);
        }
    }

    public static void e(String message, Throwable throwable) {
        e(DEFAULT_TAG, message, throwable);
    }

    /**
     * Logs a message only in debug builds.
     *
     * @param tag Tag for the log.
     * @param message The message to log.
     */
    public static void debugOnly(String tag, String message) {
        if (BuildConfig.DEBUG && isLoggingEnabled) {
            Log.d(tag, formatMessage(message));
        }
    }

    /**
     * Logs an exception with a formatted timestamp.
     *
     * @param tag Tag for the log.
     * @param throwable Throwable to log.
     */
    public static void logException(String tag, Throwable throwable) {
        if (isLoggingEnabled && throwable != null) {
            Log.e(tag, formatMessage("Exception: " + throwable.getMessage()), throwable);
        }
    }

    /**
     * Returns the current timestamp formatted.
     *
     * @return The formatted timestamp.
     */
    private static String getCurrentTimestamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault()).format(new Date());
    }

    /**
     * Formats a log message with a timestamp.
     *
     * @param message The message to format.
     * @return The formatted message.
     */
    private static String formatMessage(String message) {
        return getCurrentTimestamp() + " - " + message;
    }
} 
