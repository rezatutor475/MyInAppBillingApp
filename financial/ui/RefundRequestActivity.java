package com.myinappbilling.financial.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.myinappbilling.R;
import com.myinappbilling.financial.viewmodel.FinancialViewModel;
import com.myinappbilling.financial.model.RefundRequest;

/**
 * Activity to allow users to submit a refund request.
 */
public class RefundRequestActivity extends AppCompatActivity {

    private EditText transactionIdInput;
    private EditText reasonInput;
    private EditText emailInput;
    private Button submitButton;
    private ProgressBar loadingIndicator;
    private FinancialViewModel financialViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund_request);

        transactionIdInput = findViewById(R.id.transaction_id_input);
        reasonInput = findViewById(R.id.refund_reason_input);
        emailInput = findViewById(R.id.refund_email_input);
        submitButton = findViewById(R.id.submit_refund_button);
        loadingIndicator = findViewById(R.id.loading_indicator);

        financialViewModel = new ViewModelProvider(this).get(FinancialViewModel.class);

        submitButton.setOnClickListener(v -> submitRefundRequest());
    }

    private void submitRefundRequest() {
        String transactionId = transactionIdInput.getText().toString().trim();
        String reason = reasonInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();

        if (transactionId.isEmpty() || reason.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        loadingIndicator.setVisibility(View.VISIBLE);

        RefundRequest refundRequest = new RefundRequest(transactionId, reason, email);

        financialViewModel.submitRefundRequest(refundRequest).observe(this, success -> {
            loadingIndicator.setVisibility(View.GONE);
            if (success) {
                Toast.makeText(this, "Refund request submitted successfully.", Toast.LENGTH_LONG).show();
                clearInputs();
                finish();
            } else {
                Toast.makeText(this, "Failed to submit refund request.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void clearInputs() {
        transactionIdInput.setText("");
        reasonInput.setText("");
        emailInput.setText("");
    }
}
