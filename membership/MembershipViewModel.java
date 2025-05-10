package com.myinappbilling.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.myinappbilling.billing.MembershipManager;
import com.myinappbilling.billing.BillingConstants;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.SkuDetails;

import java.util.List;

public class MembershipViewModel extends AndroidViewModel {

    private static final String TAG = "MembershipViewModel";
    private MembershipManager membershipManager;

    // LiveData to expose membership status to the UI
    private MutableLiveData<Boolean> isMembershipActive = new MutableLiveData<>(false);
    private MutableLiveData<String> membershipSkuId = new MutableLiveData<>(null);
    private MutableLiveData<String> errorMessage = new MutableLiveData<>(null);
    private MutableLiveData<List<SkuDetails>> availableSkuDetails = new MutableLiveData<>(null);

    public MembershipViewModel(Application application) {
        super(application);
        // Initialize the MembershipManager with a listener to update UI on membership status changes
        membershipManager = new MembershipManager(application, this::handlePurchaseUpdate, new MembershipManager.MembershipStatusListener() {
            @Override
            public void onMembershipStatusChanged(boolean isActive, String skuId) {
                isMembershipActive.postValue(isActive); // Update the membership active status
                membershipSkuId.postValue(skuId); // Update the SKU ID of the active membership
            }
        });
    }

    /**
     * Initiates the purchase flow for a given membership SKU.
     *
     * @param skuId The SKU ID of the membership to purchase.
     */
    public void initiatePurchaseFlow(String skuId) {
        if (skuId != null && !skuId.isEmpty()) {
            membershipManager.initiatePurchaseFlow(skuId, BillingConstants.SKU_TYPE_SUBS);
        } else {
            Log.e(TAG, "Invalid SKU ID.");
            errorMessage.postValue("Invalid SKU ID.");
        }
    }

    /**
     * Queries for available membership options (e.g., subscription levels).
     */
    public void queryMembershipOptions() {
        List<String> skuList = List.of(BillingConstants.SKU_GOLD_MEMBERSHIP, BillingConstants.SKU_SILVER_MEMBERSHIP, BillingConstants.SKU_BRONZE_MEMBERSHIP);
        membershipManager.queryMembershipOptions(skuList);
    }

    /**
     * Restores previous memberships (in case of reinstall or new device).
     */
    public void restoreMemberships() {
        membershipManager.restoreMemberships();
    }

    /**
     * Handles the result of a purchase update from Google Play.
     *
     * @param billingResult The result of the purchase update.
     * @param purchases    List of purchases that were updated.
     */
    private void handlePurchaseUpdate(BillingResult billingResult, List<Purchase> purchases) {
        if (billingResult.getResponseCode() == BillingResult.ResponseCode.OK && purchases != null && !purchases.isEmpty()) {
            for (Purchase purchase : purchases) {
                membershipManager.handlePurchaseUpdate(billingResult, purchases);
            }
        } else {
            Log.e(TAG, "Purchase update failed: " + billingResult.getDebugMessage());
            errorMessage.postValue("Failed to handle purchase update.");
        }
    }

    /**
     * Check if the user has an active membership.
     */
    public void checkActiveMembership() {
        boolean hasActiveMembership = membershipManager.hasActiveMembership();
        isMembershipActive.postValue(hasActiveMembership);
    }

    /**
     * Get the expiry date of the user's active membership subscription.
     *
     * @return The expiry date of the active membership, or 0 if no active membership.
     */
    public long getMembershipExpiryDate() {
        return membershipManager.getMembershipExpiryDate();
    }

    /**
     * Get the current membership status.
     *
     * @return LiveData indicating if the membership is active.
     */
    public LiveData<Boolean> isMembershipActive() {
        return isMembershipActive;
    }

    /**
     * Get the SKU ID of the active membership (if available).
     *
     * @return LiveData containing the SKU ID of the active membership.
     */
    public LiveData<String> getMembershipSkuId() {
        return membershipSkuId;
    }

    /**
     * Get error messages, if any.
     *
     * @return LiveData containing the error message.
     */
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    /**
     * Check if a specific SKU is owned by the user.
     *
     * @param skuId The SKU ID to check ownership for.
     */
    public void checkIfSkuOwned(String skuId) {
        boolean isOwned = membershipManager.isSkuOwned(skuId);
        if (isOwned) {
            Log.d(TAG, "SKU " + skuId + " is already owned.");
            membershipSkuId.postValue(skuId);
        } else {
            Log.d(TAG, "SKU " + skuId + " is not owned.");
            errorMessage.postValue("The user does not own the SKU: " + skuId);
        }
    }

    /**
     * Acknowledges a purchase to complete the transaction (for subscriptions/consumables).
     *
     * @param purchase The purchase to acknowledge.
     */
    public void acknowledgePurchase(Purchase purchase) {
        if (purchase != null) {
            membershipManager.acknowledgePurchase(purchase);
        } else {
            Log.e(TAG, "Purchase is null. Cannot acknowledge.");
            errorMessage.postValue("Unable to acknowledge purchase.");
        }
    }

    /**
     * Consumes a consumable purchase.
     *
     * @param purchase The consumable purchase to consume.
     */
    public void consumePurchase(Purchase purchase) {
        if (purchase != null && BillingConstants.SKU_TYPE_INAPP.equals(purchase.getSku())) {
            membershipManager.consumePurchase(purchase);
        } else {
            Log.e(TAG, "Invalid purchase or non-consumable SKU. Cannot consume.");
            errorMessage.postValue("Invalid purchase. Cannot consume.");
        }
    }

    /**
     * Handles subscription renewal logic.
     * This checks whether the subscription has been renewed or expired.
     */
    public void handleSubscriptionRenewal(Purchase purchase) {
        if (purchase != null && purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
            // Check if the subscription is renewed or expired based on expiry date or subscription status.
            long expiryDate = purchase.getPurchaseTime();
            boolean isRenewed = System.currentTimeMillis() < expiryDate; // Example check

            if (isRenewed) {
                Log.d(TAG, "Subscription has been renewed.");
                isMembershipActive.postValue(true);
            } else {
                Log.d(TAG, "Subscription has expired.");
                isMembershipActive.postValue(false);
            }
        }
    }

    /**
     * Get available SKUs for display or purchase.
     * This is useful to get product details for displaying in the UI.
     *
     * @return LiveData containing the list of available SKU details.
     */
    public LiveData<List<SkuDetails>> getAvailableSkuDetails() {
        return availableSkuDetails;
    }
}
