package com.myinappbilling.billing;

import android.content.Context;
import android.util.Log;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.PurchasesUpdatedListener;

import java.util.List;

public class MembershipManager {

    private static final String TAG = "MembershipManager";
    private BillingRepository billingRepository;
    private Context context;
    private MembershipStatusListener membershipStatusListener;

    public MembershipManager(Context context, PurchasesUpdatedListener purchasesUpdatedListener, MembershipStatusListener membershipStatusListener) {
        this.context = context;
        this.billingRepository = new BillingRepository(context, purchasesUpdatedListener);
        this.membershipStatusListener = membershipStatusListener;
    }

    /**
     * Initiates a purchase flow for a membership SKU.
     * 
     * @param skuId SKU ID of the membership to purchase.
     * @param skuType Type of product (e.g., subscription or in-app product).
     */
    public void initiatePurchaseFlow(String skuId, String skuType) {
        if (BillingConstants.isValidSku(skuId)) {
            Log.d(TAG, "Initiating purchase flow for SKU: " + skuId);
            billingRepository.initiatePurchaseFlow(skuId, skuType);
        } else {
            Log.e(TAG, "Invalid SKU ID: " + skuId);
        }
    }

    /**
     * Queries available membership options (e.g., subscription levels) from Google Play.
     * 
     * @param skuList List of SKU IDs for membership products.
     */
    public void queryMembershipOptions(List<String> skuList) {
        billingRepository.queryProductDetails(skuList, BillingConstants.SKU_TYPE_SUBS, new BillingClientManager.SkuDetailsCallback() {
            @Override
            public void onSkuDetailsQuerySuccess(List<SkuDetails> skuDetailsList) {
                if (skuDetailsList != null && !skuDetailsList.isEmpty()) {
                    for (SkuDetails skuDetails : skuDetailsList) {
                        Log.d(TAG, "Available membership: " + skuDetails.getTitle() + " - " + skuDetails.getPrice());
                    }
                } else {
                    Log.d(TAG, "No membership options available.");
                }
            }

            @Override
            public void onSkuDetailsQueryFailed(String error) {
                Log.e(TAG, "Failed to query membership options: " + error);
            }
        });
    }

    /**
     * Restores previous memberships for the user.
     * This is useful after reinstallation or on new devices.
     */
    public void restoreMemberships() {
        billingRepository.restorePurchases();
    }

    /**
     * Validates the purchase of a membership.
     * This checks if the user has an active or valid subscription.
     * 
     * @param purchase The purchase object to validate.
     */
    public void validateMembershipPurchase(Purchase purchase) {
        if (purchase != null && BillingConstants.PURCHASE_STATE_PURCHASED == purchase.getPurchaseState()) {
            Log.d(TAG, "Membership is valid. SKU: " + purchase.getSku());
            membershipStatusListener.onMembershipStatusChanged(true, purchase.getSku());
        } else {
            Log.e(TAG, "Invalid membership or purchase state.");
            membershipStatusListener.onMembershipStatusChanged(false, null);
        }
    }

    /**
     * Handles the purchase update (success or failure) from Google Play.
     * 
     * @param billingResult The result of the purchase update.
     * @param purchases List of purchases, if the update is successful.
     */
    public void handlePurchaseUpdate(BillingResult billingResult, List<Purchase> purchases) {
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && purchases != null) {
            for (Purchase purchase : purchases) {
                validateMembershipPurchase(purchase);
            }
        } else {
            Log.e(TAG, "Failed to handle purchase update: " + billingResult.getDebugMessage());
            membershipStatusListener.onMembershipStatusChanged(false, null);
        }
    }

    /**
     * Acknowledges a purchase if it is valid (necessary for consumables or subscriptions).
     * 
     * @param purchase The purchase object to acknowledge.
     */
    public void acknowledgePurchase(Purchase purchase) {
        if (purchase != null) {
            billingRepository.acknowledgePurchase(purchase);
        } else {
            Log.e(TAG, "Cannot acknowledge purchase, purchase is null.");
        }
    }

    /**
     * Consumes a purchase if it is a consumable item.
     * 
     * @param purchase The consumable purchase to consume.
     */
    public void consumePurchase(Purchase purchase) {
        if (purchase != null) {
            billingRepository.consumePurchase(purchase);
        } else {
            Log.e(TAG, "Cannot consume purchase, purchase is null.");
        }
    }

    /**
     * Check if the user has an active membership subscription.
     * This will validate the user's current subscription state.
     * 
     * @return True if the user has an active membership, false otherwise.
     */
    public boolean hasActiveMembership() {
        List<Purchase> purchases = billingRepository.getPurchases(); // Assuming BillingRepository has a method to get the current purchases
        if (purchases != null && !purchases.isEmpty()) {
            for (Purchase purchase : purchases) {
                if (purchase.getSku().equals(BillingConstants.SKU_GOLD_MEMBERSHIP) && purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
                    Log.d(TAG, "Active membership found: " + purchase.getSku());
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Get the expiry date of the user's active membership subscription.
     * 
     * @return The expiry date of the subscription as a long timestamp (milliseconds).
     */
    public long getMembershipExpiryDate() {
        List<Purchase> purchases = billingRepository.getPurchases();
        if (purchases != null && !purchases.isEmpty()) {
            for (Purchase purchase : purchases) {
                if (purchase.getSku().equals(BillingConstants.SKU_GOLD_MEMBERSHIP) && purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
                    // Assuming the purchase has an expiry date field, you can extract it here
                    return purchase.getPurchaseTime(); // Replace with actual expiry date if available
                }
            }
        }
        return 0;
    }

    /**
     * Refreshes the purchase state for the user, useful after app reinstall or restore.
     * This triggers a re-query of the user's purchase state from Google Play.
     */
    public void refreshPurchaseState() {
        billingRepository.restorePurchases(); // This can trigger re-checking the purchases
    }

    /**
     * Handle purchase failure scenarios, such as network issues, errors in Google Play.
     * 
     * @param billingResult The result of the failed purchase attempt.
     */
    public void handlePurchaseFailure(BillingResult billingResult) {
        Log.e(TAG, "Purchase failed: " + billingResult.getDebugMessage());
        membershipStatusListener.onMembershipStatusChanged(false, null);
    }

    /**
     * Interface to listen for membership status updates.
     */
    public interface MembershipStatusListener {
        void onMembershipStatusChanged(boolean isActive, String skuId);
    }
}
