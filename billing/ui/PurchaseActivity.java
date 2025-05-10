package com.myinappbilling.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.android.billingclient.api.*;
import com.myinappbilling.R;
import java.util.List;
import java.util.ArrayList;

/**
 * PurchaseActivity handles the in-app billing purchase flow.
 */
public class PurchaseActivity extends AppCompatActivity implements PurchasesUpdatedListener {

    private static final String TAG = "PurchaseActivity";
    private BillingClient billingClient;
    private List<Purchase> pendingPurchases = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);

        initializeBillingClient();
    }

    private void initializeBillingClient() {
        billingClient = BillingClient.newBuilder(this)
                .setListener(this)
                .enablePendingPurchases()
                .build();

        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    Log.d(TAG, "Billing Client Setup Successful");
                    queryAvailableProducts();
                    queryPurchases();
                } else {
                    Log.e(TAG, "Billing Setup Failed: " + billingResult.getDebugMessage());
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                Log.w(TAG, "Billing Service Disconnected");
            }
        });
    }

    private void queryAvailableProducts() {
        List<String> skuList = List.of("your_product_id_here");
        SkuDetailsParams params = SkuDetailsParams.newBuilder()
                .setSkusList(skuList)
                .setType(BillingClient.SkuType.INAPP)
                .build();

        billingClient.querySkuDetailsAsync(params, (billingResult, skuDetailsList) -> {
            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && skuDetailsList != null) {
                for (SkuDetails skuDetails : skuDetailsList) {
                    launchPurchaseFlow(skuDetails);
                }
            } else {
                Log.e(TAG, "Failed to query products: " + billingResult.getDebugMessage());
            }
        });
    }

    private void launchPurchaseFlow(SkuDetails skuDetails) {
        BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                .setSkuDetails(skuDetails)
                .build();
        billingClient.launchBillingFlow(this, flowParams);
    }

    @Override
    public void onPurchasesUpdated(BillingResult billingResult, @Nullable List<Purchase> purchases) {
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && purchases != null) {
            for (Purchase purchase : purchases) {
                handlePurchase(purchase);
            }
        } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
            Toast.makeText(this, "Purchase canceled.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error: " + billingResult.getDebugMessage(), Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Purchase failed: " + billingResult.getDebugMessage());
        }
    }

    private void handlePurchase(Purchase purchase) {
        Log.d(TAG, "Purchase successful: " + purchase.getSku());
        Toast.makeText(this, "Purchase successful!", Toast.LENGTH_SHORT).show();

        if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED && !purchase.isAcknowledged()) {
            AcknowledgePurchaseParams acknowledgePurchaseParams =
                    AcknowledgePurchaseParams.newBuilder()
                            .setPurchaseToken(purchase.getPurchaseToken())
                            .build();

            billingClient.acknowledgePurchase(acknowledgePurchaseParams, billingResult -> {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    Log.d(TAG, "Purchase acknowledged.");
                }
            });
        }
    }

    private void queryPurchases() {
        Purchase.PurchasesResult purchasesResult = billingClient.queryPurchases(BillingClient.SkuType.INAPP);
        if (purchasesResult.getBillingResult().getResponseCode() == BillingClient.BillingResponseCode.OK) {
            List<Purchase> purchases = purchasesResult.getPurchasesList();
            if (purchases != null && !purchases.isEmpty()) {
                for (Purchase purchase : purchases) {
                    handlePurchase(purchase);
                }
            }
        } else {
            Log.e(TAG, "Failed to query existing purchases: " + purchasesResult.getBillingResult().getDebugMessage());
        }
    }

    private void consumePurchase(String purchaseToken) {
        ConsumeParams consumeParams = ConsumeParams.newBuilder()
                .setPurchaseToken(purchaseToken)
                .build();

        billingClient.consumeAsync(consumeParams, (billingResult, purchaseTokenResult) -> {
            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                Log.d(TAG, "Purchase consumed: " + purchaseTokenResult);
            } else {
                Log.e(TAG, "Failed to consume purchase: " + billingResult.getDebugMessage());
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (billingClient != null && billingClient.isReady()) {
            billingClient.endConnection();
        }
        super.onDestroy();
    }
}
