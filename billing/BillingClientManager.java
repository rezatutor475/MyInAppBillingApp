package com.myinappbilling.billing;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.PurchaseHistoryResponseListener;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.BillingFlowParams;

import java.util.List;

public class BillingClientManager {

    private static final String TAG = "BillingClientManager";
    private BillingClient billingClient;
    private Context context;
    private PurchasesUpdatedListener purchasesUpdatedListener;

    public BillingClientManager(Context context, PurchasesUpdatedListener purchasesUpdatedListener) {
        this.context = context;
        this.purchasesUpdatedListener = purchasesUpdatedListener;
        this.billingClient = BillingClient.newBuilder(context)
                .setListener(purchasesUpdatedListener)
                .build();
    }

    /**
     * Start the connection with Google Play Billing client.
     */
    public void startConnection(BillingClientStateListener billingClientStateListener) {
        if (billingClient.isReady()) {
            Log.d(TAG, "BillingClient is already ready.");
        } else {
            billingClient.startConnection(billingClientStateListener);
        }
    }

    /**
     * End the connection with Google Play Billing client.
     */
    public void endConnection() {
        if (billingClient.isReady()) {
            billingClient.endConnection();
            Log.d(TAG, "BillingClient connection ended.");
        }
    }

    /**
     * Check if the BillingClient is ready for operations.
     */
    public boolean isBillingClientReady() {
        return billingClient.isReady();
    }

    /**
     * Query product details (Price, Title, Description) from Google Play.
     */
    public void querySkuDetailsAsync(List<String> skuList, String skuType, final SkuDetailsCallback callback) {
        SkuDetailsParams params = SkuDetailsParams.newBuilder()
                .setSkusList(skuList)
                .setType(skuType)
                .build();

        billingClient.querySkuDetailsAsync(params, (billingResult, skuDetailsList) -> {
            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && skuDetailsList != null) {
                callback.onSkuDetailsQuerySuccess(skuDetailsList);
            } else {
                callback.onSkuDetailsQueryFailed(billingResult.getDebugMessage());
            }
        });
    }

    /**
     * Initiate the purchase flow for a given SKU (product).
     */
    public void initiatePurchaseFlow(String skuId, String skuType) {
        BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                .setSkuDetails(new SkuDetails(skuId))
                .setType(skuType)
                .build();
        
        billingClient.launchBillingFlow((Activity) context, billingFlowParams);
    }

    /**
     * Restore previous purchases after reinstall or on new devices.
     */
    public void restorePurchases() {
        billingClient.queryPurchasesAsync(BillingClient.SkuType.SUBS, (billingResult, purchases) -> {
            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && purchases != null) {
                // Handle restored purchases here (e.g., updating UI or granting access to content)
                Log.d(TAG, "Restored purchases: " + purchases.size());
            } else {
                Log.e(TAG, "Restore purchases failed: " + billingResult.getDebugMessage());
            }
        });
    }

    /**
     * Check if a purchase is valid (i.e., not refunded).
     */
    public boolean isPurchaseValid(Purchase purchase) {
        // Check if the purchase has been acknowledged and is valid.
        return purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED;
    }

    /**
     * Acknowledge a purchase to confirm it was received by the app.
     */
    public void acknowledgePurchase(Purchase purchase) {
        if (purchase != null && purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
            billingClient.acknowledgePurchase(
                    purchase.getPurchaseToken(),
                    billingResult -> {
                        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                            Log.d(TAG, "Purchase acknowledged.");
                        } else {
                            Log.e(TAG, "Failed to acknowledge purchase: " + billingResult.getDebugMessage());
                        }
                    });
        }
    }

    /**
     * Handle and manage consumable products by consuming them after use.
     */
    public void consumePurchase(Purchase purchase) {
        ConsumeParams consumeParams = ConsumeParams.newBuilder()
                .setPurchaseToken(purchase.getPurchaseToken())
                .build();

        billingClient.consumeAsync(consumeParams, new ConsumeResponseListener() {
            @Override
            public void onConsumeResponse(BillingResult billingResult, String purchaseToken) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    Log.d(TAG, "Consumable purchase consumed: " + purchaseToken);
                } else {
                    Log.e(TAG, "Failed to consume purchase: " + billingResult.getDebugMessage());
                }
            }
        });
    }

    /**
     * Fetch the purchase history of the user (useful for checking past transactions).
     */
    public void queryPurchaseHistory() {
        billingClient.queryPurchaseHistoryAsync(BillingClient.SkuType.SUBS, new PurchaseHistoryResponseListener() {
            @Override
            public void onPurchaseHistoryResponse(BillingResult billingResult, List<Purchase> purchaseHistoryList) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && purchaseHistoryList != null) {
                    // Handle the purchase history (e.g., restore access to previously purchased items)
                    Log.d(TAG, "Purchase History: " + purchaseHistoryList.size());
                } else {
                    Log.e(TAG, "Failed to retrieve purchase history: " + billingResult.getDebugMessage());
                }
            }
        });
    }

    // Callback interface for SKU details query
    public interface SkuDetailsCallback {
        void onSkuDetailsQuerySuccess(List<SkuDetails> skuDetailsList);
        void onSkuDetailsQueryFailed(String error);
    }
}
