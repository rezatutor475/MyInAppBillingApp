package com.myinappbilling.email.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.myinappbilling.email.R;
import com.myinappbilling.email.model.EmailMessage;
import com.myinappbilling.email.viewmodel.EmailViewModel;

/**
 * Activity for composing, sending, saving drafts, and discarding email drafts.
 */
public class ComposeEmailActivity extends AppCompatActivity {

    private EditText recipientEditText;
    private EditText subjectEditText;
    private EditText bodyEditText;
    private Button sendButton;
    private Button saveDraftButton;
    private Button discardButton;

    private EmailViewModel emailViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_email);

        initViews();
        initViewModel();
        setupButtonListeners();
    }

    private void initViews() {
        recipientEditText = findViewById(R.id.editTextRecipient);
        subjectEditText = findViewById(R.id.editTextSubject);
        bodyEditText = findViewById(R.id.editTextBody);
        sendButton = findViewById(R.id.buttonSend);
        saveDraftButton = findViewById(R.id.buttonSaveDraft);
        discardButton = findViewById(R.id.buttonDiscard);
    }

    private void initViewModel() {
        emailViewModel = new ViewModelProvider(this).get(EmailViewModel.class);

        emailViewModel.getEmailSentStatus().observe(this, success -> {
            if (success) {
                Toast.makeText(this, "Email sent successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to send email", Toast.LENGTH_SHORT).show();
            }
        });

        emailViewModel.getDraftSavedStatus().observe(this, saved -> {
            if (saved) {
                Toast.makeText(this, "Draft saved successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to save draft", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupButtonListeners() {
        sendButton.setOnClickListener(v -> sendEmail());
        saveDraftButton.setOnClickListener(v -> saveDraft());
        discardButton.setOnClickListener(v -> discardDraft());
    }

    private void sendEmail() {
        String recipient = recipientEditText.getText().toString().trim();
        String subject = subjectEditText.getText().toString().trim();
        String body = bodyEditText.getText().toString().trim();

        if (recipient.isEmpty() || subject.isEmpty() || body.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        EmailMessage emailMessage = new EmailMessage(recipient, subject, body);
        emailViewModel.sendEmail(emailMessage);
    }

    private void saveDraft() {
        String recipient = recipientEditText.getText().toString().trim();
        String subject = subjectEditText.getText().toString().trim();
        String body = bodyEditText.getText().toString().trim();

        if (recipient.isEmpty() && subject.isEmpty() && body.isEmpty()) {
            Toast.makeText(this, "Nothing to save", Toast.LENGTH_SHORT).show();
            return;
        }

        EmailMessage draft = new EmailMessage(recipient, subject, body);
        emailViewModel.saveDraft(draft);
    }

    private void discardDraft() {
        recipientEditText.setText("");
        subjectEditText.setText("");
        bodyEditText.setText("");
        Toast.makeText(this, "Draft discarded", Toast.LENGTH_SHORT).show();
    }
}
