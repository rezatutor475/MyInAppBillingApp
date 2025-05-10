package com.myinappbilling.creditcardreceipt.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.lifecycle.ViewModelProvider;

import com.myinappbilling.creditcardreceipt.R;
import com.myinappbilling.creditcardreceipt.model.Receipt;
import com.myinappbilling.creditcardreceipt.viewmodel.ReceiptViewModel;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Activity to display the details of a selected receipt with advanced features, including sharing.
 */
public class ReceiptDetailsActivity extends AppCompatActivity {

    private ReceiptViewModel receiptViewModel;
    private TextView merchantNameTextView;
    private TextView amountTextView;
    private TextView dateTextView;
    private TextView itemsTextView;
    private TextView transactionIdTextView;
    private Receipt currentReceipt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_details);

        initViews();
        setupViewModel();
    }

    /**
     * Initializes the UI components.
     */
    private void initViews() {
        merchantNameTextView = findViewById(R.id.text_merchant_name);
        amountTextView = findViewById(R.id.text_amount);
        dateTextView = findViewById(R.id.text_date);
        itemsTextView = findViewById(R.id.text_items);
        transactionIdTextView = findViewById(R.id.text_transaction_id);
    }

    /**
     * Sets up the ViewModel and observes data changes.
     */
    private void setupViewModel() {
        receiptViewModel = new ViewModelProvider(this).get(ReceiptViewModel.class);
        observeSelectedReceipt();
    }

    /**
     * Observes the selected receipt from ViewModel and populates the UI.
     */
    private void observeSelectedReceipt() {
        receiptViewModel.getSelectedReceipt().observe(this, receipt -> {
            if (receipt != null) {
                currentReceipt = receipt;
                displayReceiptDetails(receipt);
            } else {
                Toast.makeText(this, "No receipt selected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Displays receipt details in the UI components.
     *
     * @param receipt The receipt to display
     */
    private void displayReceiptDetails(Receipt receipt) {
        merchantNameTextView.setText(receipt.getMerchantName());
        amountTextView.setText(String.format(Locale.getDefault(), "$%.2f", receipt.getTotalAmount()));

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        dateTextView.setText(sdf.format(receipt.getTransactionDate()));

        StringBuilder itemsBuilder = new StringBuilder();
        if (receipt.getItems() != null && !receipt.getItems().isEmpty()) {
            for (String item : receipt.getItems()) {
                itemsBuilder.append("\u2022 ").append(item).append("\n");
            }
        } else {
            itemsBuilder.append("No items available.");
        }
        itemsTextView.setText(itemsBuilder.toString());

        if (receipt.getTransactionId() != null) {
            transactionIdTextView.setText(receipt.getTransactionId());
        } else {
            transactionIdTextView.setText("N/A");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_receipt_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_share) {
            shareReceipt();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Shares the current receipt details through available sharing apps.
     */
    private void shareReceipt() {
        if (currentReceipt == null) {
            Toast.makeText(this, "No receipt to share", Toast.LENGTH_SHORT).show();
            return;
        }

        StringBuilder shareContent = new StringBuilder();
        shareContent.append("Merchant: ").append(currentReceipt.getMerchantName()).append("\n");
        shareContent.append("Amount: $").append(String.format(Locale.getDefault(), "%.2f", currentReceipt.getTotalAmount())).append("\n");

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        shareContent.append("Date: ").append(sdf.format(currentReceipt.getTransactionDate())).append("\n");

        shareContent.append("Items:\n");
        if (currentReceipt.getItems() != null && !currentReceipt.getItems().isEmpty()) {
            for (String item : currentReceipt.getItems()) {
                shareContent.append(" - ").append(item).append("\n");
            }
        } else {
            shareContent.append("No items listed.\n");
        }

        shareContent.append("Transaction ID: ")
                .append(currentReceipt.getTransactionId() != null ? currentReceipt.getTransactionId() : "N/A");

        ShareCompat.IntentBuilder
                .from(this)
                .setType("text/plain")
                .setChooserTitle("Share Receipt")
                .setText(shareContent.toString())
                .startChooser();
    }
}
