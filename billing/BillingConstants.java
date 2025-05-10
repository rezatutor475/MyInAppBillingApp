package com.myinappbilling.billing;

/**
 * BillingConstants class holds constants related to the in-app billing process.
 * These constants are used to standardize SKU IDs, product types, and response codes
 * to ensure consistency throughout the application.
 */
public class BillingConstants {

    // SKU Types (Product or Subscription)
    public static final String SKU_TYPE_INAPP = "inapp";  // For one-time purchases
    public static final String SKU_TYPE_SUBS = "subs";    // For subscriptions
    public static final String SKU_TYPE_CONSUMABLE = "consumable"; // For consumables

    // Example SKU IDs for different products
    public static final String SKU_GOLD_MEMBERSHIP = "gold_membership";
    public static final String SKU_SILVER_MEMBERSHIP = "silver_membership";
    public static final String SKU_BRONZE_MEMBERSHIP = "bronze_membership";
    public static final String SKU_Premium_CONSUMABLE = "premium_consumable"; // New Consumable SKU
    
    // Response Codes for Billing
    public static final int RESPONSE_OK = 0;
    public static final int RESPONSE_USER_CANCELED = 1;
    public static final int RESPONSE_SERVICE_UNAVAILABLE = 2;
    public static final int RESPONSE_BILLING_UNAVAILABLE = 3;
    public static final int RESPONSE_ITEM_UNAVAILABLE = 4;
    public static final int RESPONSE_DEVELOPER_ERROR = 5;
    public static final int RESPONSE_ERROR = 6;
    public static final int RESPONSE_ITEM_ALREADY_OWNED = 7;

    // Response Code Messages
    public static final String RESPONSE_MESSAGE_OK = "Operation Successful";
    public static final String RESPONSE_MESSAGE_USER_CANCELED = "User canceled the purchase flow.";
    public static final String RESPONSE_MESSAGE_SERVICE_UNAVAILABLE = "Billing service is unavailable.";
    public static final String RESPONSE_MESSAGE_BILLING_UNAVAILABLE = "Billing is not available.";
    public static final String RESPONSE_MESSAGE_ITEM_UNAVAILABLE = "Item not available for purchase.";
    public static final String RESPONSE_MESSAGE_DEVELOPER_ERROR = "Developer error occurred.";
    public static final String RESPONSE_MESSAGE_ERROR = "Unknown error occurred.";
    public static final String RESPONSE_MESSAGE_ITEM_ALREADY_OWNED = "Item is already owned by the user.";

    // Google Play Billing API Settings
    public static final String API_VERSION = "3";  // API Version for billing client
    public static final String API_PACKAGE_NAME = "com.android.vending";  // Package name for the Play Store
    public static final String PLAY_STORE_PACKAGE = "com.android.vending";  // Play Store package

    // SKU List for querying
    public static final String[] ALL_SKUS = {
        SKU_GOLD_MEMBERSHIP,
        SKU_SILVER_MEMBERSHIP,
        SKU_BRONZE_MEMBERSHIP,
        SKU_Premium_CONSUMABLE
    };

    // Purchase States
    public static final int PURCHASE_STATE_PURCHASED = 1;
    public static final int PURCHASE_STATE_PENDING = 2;
    public static final int PURCHASE_STATE_CANCELLED = 3;

    // Prevent instantiation
    private BillingConstants() {
        throw new UnsupportedOperationException("Cannot instantiate BillingConstants.");
    }

    /**
     * Get response message based on response code
     *
     * @param responseCode Response code to get the corresponding message
     * @return Response message as a string
     */
    public static String getResponseMessage(int responseCode) {
        switch (responseCode) {
            case RESPONSE_OK:
                return RESPONSE_MESSAGE_OK;
            case RESPONSE_USER_CANCELED:
                return RESPONSE_MESSAGE_USER_CANCELED;
            case RESPONSE_SERVICE_UNAVAILABLE:
                return RESPONSE_MESSAGE_SERVICE_UNAVAILABLE;
            case RESPONSE_BILLING_UNAVAILABLE:
                return RESPONSE_MESSAGE_BILLING_UNAVAILABLE;
            case RESPONSE_ITEM_UNAVAILABLE:
                return RESPONSE_MESSAGE_ITEM_UNAVAILABLE;
            case RESPONSE_DEVELOPER_ERROR:
                return RESPONSE_MESSAGE_DEVELOPER_ERROR;
            case RESPONSE_ERROR:
                return RESPONSE_MESSAGE_ERROR;
            case RESPONSE_ITEM_ALREADY_OWNED:
                return RESPONSE_MESSAGE_ITEM_ALREADY_OWNED;
            default:
                return RESPONSE_MESSAGE_ERROR;
        }
    }

    /**
     * Validate SKU ID for purchase.
     * 
     * @param skuId The SKU ID to be validated.
     * @return True if valid, false otherwise.
     */
    public static boolean isValidSku(String skuId) {
        for (String sku : ALL_SKUS) {
            if (sku.equals(skuId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Validate SKU type.
     *
     * @param skuType The SKU type to validate.
     * @return True if valid, false otherwise.
     */
    public static boolean isValidSkuType(String skuType) {
        return SKU_TYPE_INAPP.equals(skuType) || SKU_TYPE_SUBS.equals(skuType) || SKU_TYPE_CONSUMABLE.equals(skuType);
    }

    /**
     * Log errors or details related to billing processes.
     *
     * @param tag Tag for the log entry
     * @param message Error message or details
     */
    public static void logBillingError(String tag, String message) {
        Log.e(tag, message);
    }
}
