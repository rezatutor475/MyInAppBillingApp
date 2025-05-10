package com.myinappbilling.billing;

import android.content.Context;
import android.util.Log;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.BillingFlowParams;

import java.util.List;

public class BillingRepository {

    private static final String TAG = "BillingRepository";
    private BillingClientManager billingClientManager;

    public BillingRepository(Context context, PurchasesUpdatedListener purchasesUpdatedListener) {
        billingClientManager = new BillingClientManager(context, purchasesUpdatedListener);
    }

    /**
     * Initiates the purchase flow for a given SKU.
     * 
     * @param skuId The SKU ID of the product to purchase.
     * @param skuType The type of product (e.g., subscription or in-app product).
     */
    public void initiatePurchaseFlow(String skuId, String skuType) {
        if (billingClientManager.isBillingClientReady()) {
            Log.d(TAG, "Starting purchase flow for SKU: " + skuId);
            billingClientManager.initiatePurchaseFlow(skuId, skuType);
        } else {
            Log.e(TAG, "BillingClient is not ready. Unable to start purchase flow.");
        }
    }

    /**
     * Queries product details for a list of SKU IDs.
     * 
     * @param skuList List of SKU IDs to query.
     * @param skuType Type of product (e.g., subscription, in-app product).
     * @param callback Callback to handle the result of the SKU details query.
     */
    public void queryProductDetails(List<String> skuList, String skuType, BillingClientManager.SkuDetailsCallback callback) {
        if (billingClientManager.isBillingClientReady()) {
            Log.d(TAG, "Querying product details for SKUs: " + skuList);
            billingClientManager.querySkuDetailsAsync(skuList, skuType, callback);
        } else {
            Log.e(TAG, "BillingClient is not ready. Unable to query SKU details.");
        }
    }

    /**
     * Restores previous purchases (for reinstallation or new devices).
     */
    public void restorePurchases() {
        if (billingClientManager.isBillingClientReady()) {
            Log.d(TAG, "Restoring previous purchases...");
            billingClientManager.restorePurchases();
        } else {
            Log.e(TAG, "BillingClient is not ready. Unable to restore purchases.");
        }
    }

    /**
     * Handles purchase updates (e.g., successful purchase, failure).
     * 
     * @param billingResult The result of the purchase update.
     * @param purchases List of purchase objects representing the user's purchases.
     */
    public void handlePurchaseUpdates(BillingResult billingResult, List<Purchase> purchases) {
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && purchases != null) {
            for (Purchase purchase : purchases) {
                // You can add logic to handle the purchase based on SKU, subscription, etc.
                Log.d(TAG, "Purchase successful for SKU: " + purchase.getSku());
                // Optionally acknowledge or consume the purchase here.
            }
        } else {
            Log.e(TAG, "Purchase update failed: " + billingResult.getDebugMessage());
        }
    }

    /**
     * Validates a purchase (for example, checking if the user has purchased a subscription).
     * 
     * @param purchase The purchase object to validate.
     * @return True if the purchase is valid, false otherwise.
     */
    public boolean validatePurchase(Purchase purchase) {
        // Implement any purchase validation logic (e.g., checking for valid subscriptions)
        return purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED;
    }

    /**
     * Acknowledges a purchase (needed for consumable items or subscriptions).
     * 
     * @param purchase The purchase to acknowledge.
     */
    public void acknowledgePurchase(Purchase purchase) {
        if (purchase != null) {
            billingClientManager.acknowledgePurchase(purchase);
        } else {
            Log.e(TAG, "Unable to acknowledge purchase. Purchase is null.");
        }
    }

    /**
     * Consumes a purchase (useful for consumables).
     * 
     * @param purchase The purchase to consume.
     */
    public void consumePurchase(Purchase purchase) {
        if (purchase != null) {
            billingClientManager.consumePurchase(purchase);
        } else {
            Log.e(TAG, "Unable to consume purchase. Purchase is null.");
        }
    }

    /**
     * Check if the user has an active subscription.
     * 
     * @param sku The SKU of the subscription.
     * @return True if the user has an active subscription, false otherwise.
     */
    public boolean checkSubscriptionStatus(String sku) {
        // Here, you can check the current purchase state from the purchases list.
        List<Purchase> purchases = billingClientManager.queryPurchases(BillingClient.SkuType.SUBS);
        for (Purchase purchase : purchases) {
            if (purchase.getSku().equals(sku) && purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
                Log.d(TAG, "Active subscription found for SKU: " + sku);
                return true;
            }
        }
        Log.d(TAG, "No active subscription found for SKU: " + sku);
        return false;
    }

    /**
     * Logs billing-related events for analytics or debugging purposes.
     * 
     * @param event The event description to log.
     */
    private void logBillingEvent(String event) {
        Log.d(TAG, "Billing Event: " + event);
    }

    /**
     * Fetches the user's purchase history for restoring purchases or handling subscription renewals.
     * 
     * @return List of Purchase objects representing the user's purchase history.
     */
    public List<Purchase> getPurchasesHistory() {
        if (billingClientManager.isBillingClientReady()) {
            Log.d(TAG, "Fetching purchase history...");
            return billingClientManager.queryPurchases(BillingClient.SkuType.INAPP);
        } else {
            Log.e(TAG, "BillingClient is not ready. Unable to fetch purchase history.");
            return null;
        }
    }

    /**
     * Validates the subscription status for a user.
     * 
     * @param purchase The subscription purchase object to validate.
     * @return True if the subscription is valid, false otherwise.
     */
    public boolean validateSubscription(Purchase purchase) {
        if (purchase != null && purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
            Log.d(TAG, "Valid subscription found.");
            return true;
        } else {
            Log.d(TAG, "Subscription is not valid.");
            return false;
        }
    }
}
