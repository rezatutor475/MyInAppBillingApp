package com.myinappbilling.email.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.myinappbilling.email.R;

/**
 * Activity for displaying the detailed status of a specific email.
 * Also allows sharing or resending the email.
 */
public class EmailStatusActivity extends AppCompatActivity {

    private TextView subjectTextView;
    private TextView recipientTextView;
    private TextView bodyTextView;

    private String subject;
    private String recipient;
    private String body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_status);

        initViews();
        displayEmailDetails();
    }

    private void initViews() {
        subjectTextView = findViewById(R.id.textViewSubject);
        recipientTextView = findViewById(R.id.textViewRecipient);
        bodyTextView = findViewById(R.id.textViewBody);
    }

    private void displayEmailDetails() {
        if (getIntent() != null) {
            subject = getIntent().getStringExtra("email_subject");
            recipient = getIntent().getStringExtra("email_recipient");
            body = getIntent().getStringExtra("email_body");

            subjectTextView.setText(subject != null ? subject : "No Subject");
            recipientTextView.setText(recipient != null ? recipient : "No Recipient");
            bodyTextView.setText(body != null ? body : "No Content");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_email_status, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_share_email) {
            shareEmail();
            return true;
        } else if (item.getItemId() == R.id.action_resend_email) {
            resendEmail();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void shareEmail() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        shareIntent.putExtra(Intent.EXTRA_TEXT, formatEmailContent());
        startActivity(Intent.createChooser(shareIntent, "Share Email via"));
    }

    private void resendEmail() {
        Intent resendIntent = new Intent(this, ComposeEmailActivity.class);
        resendIntent.putExtra("email_subject", subject);
        resendIntent.putExtra("email_recipient", recipient);
        resendIntent.putExtra("email_body", body);
        startActivity(resendIntent);
        Toast.makeText(this, "Preparing to resend email", Toast.LENGTH_SHORT).show();
    }

    private String formatEmailContent() {
        return "Subject: " + (subject != null ? subject : "") + "\n\n"
                + "To: " + (recipient != null ? recipient : "") + "\n\n"
                + (body != null ? body : "");
    }
}
