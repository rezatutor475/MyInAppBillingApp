package com.myinappbilling.utils;

import android.text.TextUtils;
import android.util.Log;

import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.SkuDetails;
import com.myinappbilling.billing.BillingConstants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * MembershipUtils provides utility methods related to in-app purchases and membership status.
 * These utilities are helpful for formatting dates, validating purchases, and other common tasks.
 */
public class MembershipUtils {

    private static final String TAG = "MembershipUtils";

    /**
     * Validates a purchase to ensure it's a valid and active membership.
     * 
     * @param purchase The purchase object to validate.
     * @return True if the purchase is valid and active, otherwise false.
     */
    public static boolean isValidPurchase(Purchase purchase) {
        if (purchase == null) {
            Log.e(TAG, "Purchase is null.");
            return false;
        }

        // Check if the purchase state is 'purchased' and that the SKU matches a valid membership SKU
        if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED &&
            isValidSku(purchase.getSku())) {
            return true;
        } else {
            Log.e(TAG, "Invalid purchase or purchase state: " + purchase.getPurchaseState());
            return false;
        }
    }

    /**
     * Checks if a given SKU is valid for membership.
     *
     * @param sku The SKU to check.
     * @return True if the SKU is a valid membership SKU, otherwise false.
     */
    public static boolean isValidSku(String sku) {
        if (TextUtils.isEmpty(sku)) {
            return false;
        }

        // Check against valid membership SKUs
        return sku.equals(BillingConstants.SKU_GOLD_MEMBERSHIP) ||
               sku.equals(BillingConstants.SKU_SILVER_MEMBERSHIP) ||
               sku.equals(BillingConstants.SKU_BRONZE_MEMBERSHIP);
    }

    /**
     * Gets the human-readable expiry date for a subscription.
     * 
     * @param purchase The purchase object containing the purchase details.
     * @return A string representing the expiry date in a readable format, or "N/A" if no expiry.
     */
    public static String getSubscriptionExpiryDate(Purchase purchase) {
        if (purchase == null || purchase.getPurchaseTime() == 0) {
            return "N/A";
        }

        // Assuming that expiry date is derived from the purchase time and subscription duration.
        long expiryTime = purchase.getPurchaseTime() + getSubscriptionDuration(purchase.getSku());
        Date expiryDate = new Date(expiryTime);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(expiryDate);
    }

    /**
     * Gets the subscription duration for a given SKU.
     * In real implementations, this would depend on the subscription period defined for each SKU.
     * 
     * @param sku The SKU of the product.
     * @return The subscription duration in milliseconds.
     */
    private static long getSubscriptionDuration(String sku) {
        // Example: Gold membership is a 1-year subscription, Silver is 6 months, and Bronze is 3 months.
        switch (sku) {
            case BillingConstants.SKU_GOLD_MEMBERSHIP:
                return 365L * 24L * 60L * 60L * 1000L; // 1 year in milliseconds
            case BillingConstants.SKU_SILVER_MEMBERSHIP:
                return 182L * 24L * 60L * 60L * 1000L; // 6 months in milliseconds
            case BillingConstants.SKU_BRONZE_MEMBERSHIP:
                return 91L * 24L * 60L * 60L * 1000L; // 3 months in milliseconds
            default:
                return 0; // Invalid SKU, no duration
        }
    }

    /**
     * Formats a SKU's price for display.
     *
     * @param skuDetails The SkuDetails object containing the SKU's details.
     * @return A string representing the formatted price, or "N/A" if not available.
     */
    public static String formatSkuPrice(SkuDetails skuDetails) {
        if (skuDetails == null || skuDetails.getPrice() == null) {
            return "N/A";
        }
        return skuDetails.getPrice();
    }

    /**
     * Converts the given milliseconds time to a formatted string.
     *
     * @param timeMillis The time in milliseconds.
     * @return A formatted string representing the time (e.g., "2 days", "3 hours").
     */
    public static String formatTime(long timeMillis) {
        if (timeMillis < 0) {
            return "N/A";
        }

        long days = timeMillis / (24 * 60 * 60 * 1000);
        long hours = (timeMillis % (24 * 60 * 60 * 1000)) / (60 * 60 * 1000);
        long minutes = (timeMillis % (60 * 60 * 1000)) / (60 * 1000);

        StringBuilder timeString = new StringBuilder();
        if (days > 0) {
            timeString.append(days).append(" days ");
        }
        if (hours > 0) {
            timeString.append(hours).append(" hours ");
        }
        if (minutes > 0) {
            timeString.append(minutes).append(" minutes");
        }

        return timeString.toString();
    }

    /**
     * Logs and formats any error message that occurs during membership or billing process.
     * 
     * @param errorMessage The error message to log.
     */
    public static void logError(String errorMessage) {
        if (TextUtils.isEmpty(errorMessage)) {
            Log.e(TAG, "An unknown error occurred.");
        } else {
            Log.e(TAG, errorMessage);
        }
    }

    /**
     * Gets the remaining time until a subscription expires.
     * 
     * @param purchase The purchase object containing the purchase details.
     * @return A string representing the remaining time (e.g., "1 day", "3 hours 15 minutes").
     */
    public static String getRemainingTimeUntilExpiry(Purchase purchase) {
        if (purchase == null || purchase.getPurchaseTime() == 0) {
            return "N/A";
        }

        long expiryTime = purchase.getPurchaseTime() + getSubscriptionDuration(purchase.getSku());
        long remainingTimeMillis = expiryTime - System.currentTimeMillis();

        if (remainingTimeMillis <= 0) {
            return "Subscription expired";
        }

        return formatTime(remainingTimeMillis);
    }

    /**
     * Checks if the subscription has expired.
     * 
     * @param purchase The purchase object containing the purchase details.
     * @return True if the subscription is expired, otherwise false.
     */
    public static boolean isSubscriptionExpired(Purchase purchase) {
        if (purchase == null || purchase.getPurchaseTime() == 0) {
            return true;
        }

        long expiryTime = purchase.getPurchaseTime() + getSubscriptionDuration(purchase.getSku());
        return System.currentTimeMillis() > expiryTime;
    }

    /**
     * Returns a human-readable status for a purchase (e.g., "active", "expired", "invalid").
     * 
     * @param purchase The purchase object to check.
     * @return A string representing the status of the purchase.
     */
    public static String getPurchaseStatus(Purchase purchase) {
        if (purchase == null) {
            return "Invalid purchase";
        }

        if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
            if (isSubscriptionExpired(purchase)) {
                return "Expired";
            } else {
                return "Active";
            }
        } else {
            return "Invalid purchase state";
        }
    }

    /**
     * Formats a timestamp into a human-readable date string.
     * 
     * @param timestamp The timestamp in milliseconds.
     * @return A formatted date string (e.g., "2023-04-22 18:30:00").
     */
    public static String getFormattedDate(long timestamp) {
        Date date = new Date(timestamp);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(date);
    }

    /**
     * Logs details about a purchase (for debugging purposes).
     * 
     * @param purchase The purchase object to log.
     */
    public static void logPurchaseDetails(Purchase purchase) {
        if (purchase != null) {
            Log.d(TAG, "Purchase details: SKU = " + purchase.getSku() + ", state = " + purchase.getPurchaseState() +
                    ", time = " + getFormattedDate(purchase.getPurchaseTime()));
        } else {
            Log.e(TAG, "Purchase is null");
        }
    }
}
