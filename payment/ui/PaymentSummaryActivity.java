package com.myinappbilling.payment.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.myinappbilling.payment.R;
import com.myinappbilling.payment.model.PaymentDetails;
import com.myinappbilling.payment.viewmodel.PaymentDetailsViewModel;

/**
 * Activity to display a summary of the payment details.
 */
public class PaymentSummaryActivity extends AppCompatActivity {

    private PaymentDetailsViewModel viewModel;
    private TextView tvSummary;
    private Button btnEdit, btnSubmit, btnCancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_summary);

        viewModel = new ViewModelProvider(this).get(PaymentDetailsViewModel.class);
        tvSummary = findViewById(R.id.tvSummary);
        btnEdit = findViewById(R.id.btnEdit);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnCancel = findViewById(R.id.btnCancel);

        observeViewModel();
        setupListeners();
    }

    private void observeViewModel() {
        viewModel.getSavedPaymentDetails().observe(this, paymentDetails -> {
            if (paymentDetails != null) {
                String summary = buildSummary(paymentDetails);
                tvSummary.setText(summary);
            } else {
                tvSummary.setText("No payment details available.");
            }
        });
    }

    private void setupListeners() {
        btnEdit.setOnClickListener(v -> finish());

        btnSubmit.setOnClickListener(v -> {
            PaymentDetails details = viewModel.getSavedPaymentDetails().getValue();
            if (details != null && viewModel.validateAll(details)) {
                // Placeholder for submission logic (e.g., API call)
                tvSummary.setText("Payment submitted successfully.");
                Toast.makeText(this, "Submitted successfully", Toast.LENGTH_SHORT).show();
            } else {
                tvSummary.setText("Payment submission failed. Invalid or incomplete details.");
                Toast.makeText(this, "Submission failed", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(v -> {
            viewModel.clearPaymentDetails();
            tvSummary.setText("Payment details cleared.");
        });
    }

    private String buildSummary(PaymentDetails details) {
        return "Name: " + details.getFirstName() + " " + details.getLastName() + "\n"
                + "Email: " + details.getEmail() + "\n"
                + "Phone: " + details.getCellphoneNumber() + "\n"
                + "Address: " + details.getAddress() + ", " + details.getCity() + ", "
                + details.getProvince() + ", " + details.getCountry() + "\n"
                + "Postal Code: " + details.getPostalCode() + "\n"
                + "Account: " + details.getAccountNumber() + "\n"
                + "Card: **** **** **** " + getLast4Digits(details.getCardNumber()) + "\n"
                + "Expiration: " + details.getExpirationMonth() + "/" + details.getExpirationYear();
    }

    private String getLast4Digits(String cardNumber) {
        if (cardNumber != null && cardNumber.length() >= 4) {
            return cardNumber.substring(cardNumber.length() - 4);
        }
        return "0000";
    }
}
