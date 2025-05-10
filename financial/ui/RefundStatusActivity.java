package com.myinappbilling.financial.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.myinappbilling.R;
import com.myinappbilling.financial.model.RefundStatus;
import com.myinappbilling.financial.viewmodel.FinancialViewModel;

/**
 * Activity to display the status of a refund request based on a transaction ID.
 */
public class RefundStatusActivity extends AppCompatActivity {

    private EditText transactionIdInput;
    private TextView statusOutput;
    private Button checkStatusButton;
    private ProgressBar loadingIndicator;
    private FinancialViewModel financialViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund_status);

        transactionIdInput = findViewById(R.id.transaction_id_input);
        statusOutput = findViewById(R.id.refund_status_output);
        checkStatusButton = findViewById(R.id.check_status_button);
        loadingIndicator = findViewById(R.id.loading_indicator);

        financialViewModel = new ViewModelProvider(this).get(FinancialViewModel.class);

        checkStatusButton.setOnClickListener(v -> checkRefundStatus());
    }

    private void checkRefundStatus() {
        String transactionId = transactionIdInput.getText().toString().trim();
        if (transactionId.isEmpty()) {
            Toast.makeText(this, "Please enter a transaction ID.", Toast.LENGTH_SHORT).show();
            return;
        }

        loadingIndicator.setVisibility(View.VISIBLE);

        financialViewModel.getRefundStatus(transactionId).observe(this, refundStatus -> {
            loadingIndicator.setVisibility(View.GONE);
            if (refundStatus != null) {
                statusOutput.setText("Status: " + refundStatus.getStatus());
                if (refundStatus.getMessage() != null && !refundStatus.getMessage().isEmpty()) {
                    statusOutput.append("\nMessage: " + refundStatus.getMessage());
                }
            } else {
                statusOutput.setText("Refund status not found.");
            }
        });
    }

    // Additional functionality to clear inputs
    private void clearInputs() {
        transactionIdInput.setText("");
        statusOutput.setText("");
    }

    // Optional: Called from a button to reset the form
    public void onResetClicked(View view) {
        clearInputs();
    }
}
