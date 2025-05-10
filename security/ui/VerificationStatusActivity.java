package com.myinappbilling.security.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.myinappbilling.R;
import com.myinappbilling.security.viewmodel.SecurityViewModel;
import com.myinappbilling.security.model.VerificationStatus;

/**
 * Activity for displaying the current verification status and retry option.
 */
public class VerificationStatusActivity extends AppCompatActivity {

    private SecurityViewModel securityViewModel;
    private TextView verificationStatusText;
    private Button retryButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_status);

        verificationStatusText = findViewById(R.id.verification_status_text);
        retryButton = findViewById(R.id.retry_button);

        securityViewModel = new ViewModelProvider(this).get(SecurityViewModel.class);

        observeVerificationStatus();

        retryButton.setOnClickListener(view -> retryVerification());
    }

    private void observeVerificationStatus() {
        securityViewModel.getVerificationStatus().observe(this, status -> {
            if (status != null) {
                verificationStatusText.setText("Current Status: " + status.name());
            } else {
                verificationStatusText.setText("Verification status unavailable.");
            }
        });
    }

    private void retryVerification() {
        // Re-initiate a dummy verification or navigate to prompt screen
        Toast.makeText(this, "Retrying verification...", Toast.LENGTH_SHORT).show();
        // This could be expanded to launch SecurityPromptActivity again if necessary
        // startActivity(new Intent(this, SecurityPromptActivity.class));
    }
}
