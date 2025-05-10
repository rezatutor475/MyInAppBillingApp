package com.myinappbilling.security.ui;

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
import com.myinappbilling.security.viewmodel.SecurityViewModel;
import com.myinappbilling.security.model.VerificationStatus;

/**
 * Activity for prompting the user to enter security token and credentials.
 */
public class SecurityPromptActivity extends AppCompatActivity {

    private SecurityViewModel securityViewModel;
    private EditText tokenInput;
    private EditText credentialsInput;
    private Button verifyButton;
    private Button resetButton;
    private ProgressBar loadingIndicator;
    private TextView resultText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_prompt);

        tokenInput = findViewById(R.id.token_input);
        credentialsInput = findViewById(R.id.credentials_input);
        verifyButton = findViewById(R.id.verify_button);
        resetButton = findViewById(R.id.reset_button);
        loadingIndicator = findViewById(R.id.loading_indicator);
        resultText = findViewById(R.id.result_text);

        securityViewModel = new ViewModelProvider(this).get(SecurityViewModel.class);

        observeViewModel();

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = tokenInput.getText().toString().trim();
                String credentials = credentialsInput.getText().toString().trim();
                if (!token.isEmpty() && !credentials.isEmpty()) {
                    loadingIndicator.setVisibility(View.VISIBLE);
                    securityViewModel.verifySecurity(token, credentials);
                } else {
                    Toast.makeText(SecurityPromptActivity.this, "Please enter both token and credentials.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                securityViewModel.resetVerificationStatus();
                tokenInput.setText("");
                credentialsInput.setText("");
                resultText.setText("");
                Toast.makeText(SecurityPromptActivity.this, "Inputs and status reset.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void observeViewModel() {
        securityViewModel.getVerificationStatus().observe(this, status -> {
            loadingIndicator.setVisibility(View.GONE);
            if (status != null) {
                resultText.setText("Verification Status: " + status.name());
            } else {
                resultText.setText("Verification failed or status not available.");
            }
        });
    }
}
