package com.myinappbilling;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.myinappbilling.billing.BillingClientManager;
import com.myinappbilling.billing.BillingRepository;
import com.myinappbilling.membership.MembershipManager;
import com.myinappbilling.membership.MembershipViewModel;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BillingClientManager billingClientManager;
    private BillingRepository billingRepository;
    private MembershipViewModel membershipViewModel;

    // UI components
    private Button purchaseButton;
    private Button checkSubscriptionButton;
    private Button retryConnectionButton;
    private Button restorePurchasesButton;
    private TextView productInfoTextView;

    private static final String SKU_ID = "gold_membership";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        billingClientManager = new BillingClientManager(this);
        billingRepository = new BillingRepository(this);
        membershipViewModel = new ViewModelProvider(this).get(MembershipViewModel.class);

        purchaseButton = findViewById(R.id.purchase_button);
        checkSubscriptionButton = findViewById(R.id.check_subscription_button);
        retryConnectionButton = findViewById(R.id.retry_connection_button);
        restorePurchasesButton = findViewById(R.id.restore_purchases_button);
        productInfoTextView = findViewById(R.id.product_info_text);

        setupBillingClient();

        checkUserSubscriptionStatus();

        purchaseButton.setOnClickListener(v -> initiatePurchaseFlow());
        checkSubscriptionButton.setOnClickListener(v -> checkUserSubscriptionStatus());
        retryConnectionButton.setOnClickListener(v -> setupBillingClient());
        restorePurchasesButton.setOnClickListener(v -> billingRepository.restorePurchases());
    }

    private void setupBillingClient() {
        billingClientManager.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    Log.d("MainActivity", "BillingClient setup successful.");
                    fetchProductDetails();
                } else {
                    Log.e("MainActivity", "BillingClient setup failed: " + billingResult.getDebugMessage());
                    Toast.makeText(MainActivity.this, "Billing setup failed.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                Log.e("MainActivity", "BillingService disconnected.");
                Toast.makeText(MainActivity.this, "Billing service disconnected.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchProductDetails() {
        List<String> skuList = Arrays.asList(SKU_ID);
        SkuDetailsParams params = SkuDetailsParams.newBuilder()
                .setSkusList(skuList)
                .setType(BillingClient.SkuType.SUBS)
                .build();

        billingClientManager.querySkuDetailsAsync(params, skuDetailsList -> {
            if (skuDetailsList != null && !skuDetailsList.isEmpty()) {
                SkuDetails skuDetails = skuDetailsList.get(0);
                String info = "Product: " + skuDetails.getTitle() + "\n" +
                              "Price: " + skuDetails.getPrice() + "\n" +
                              "Description: " + skuDetails.getDescription();
                productInfoTextView.setText(info);
            } else {
                productInfoTextView.setText("Failed to load product details.");
            }
        });
    }

    private void initiatePurchaseFlow() {
        if (billingClientManager.isBillingClientReady()) {
            billingRepository.initiatePurchaseFlow(SKU_ID, this, new PurchasesUpdatedListener() {
                @Override
                public void onPurchasesUpdated(BillingResult billingResult, List<Purchase> purchases) {
                    if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && purchases != null) {
                        for (Purchase purchase : purchases) {
                            handlePurchase(purchase);
                        }
                    } else {
                        String errorMessage = "Purchase failed: " + billingResult.getDebugMessage();
                        Log.e("MainActivity", errorMessage);
                        Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, "Billing not ready.", Toast.LENGTH_SHORT).show();
        }
    }

    private void handlePurchase(Purchase purchase) {
        Log.d("MainActivity", "Purchase successful: " + purchase.getSku());
        Toast.makeText(this, "Purchase successful! " + purchase.getSku(), Toast.LENGTH_SHORT).show();

        MembershipManager.getInstance().renewSubscription();
        checkUserSubscriptionStatus();
    }

    private void checkUserSubscriptionStatus() {
        boolean isSubscribed = membershipViewModel.isUserSubscribed();

        if (isSubscribed) {
            Toast.makeText(this, "You are already subscribed!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "You are not subscribed. Consider purchasing a plan!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        billingClientManager.endConnection();
    }
}
