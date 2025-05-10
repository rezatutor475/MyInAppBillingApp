package com.myinappbilling.email.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.myinappbilling.email.R;
import com.myinappbilling.email.model.EmailMessage;
import com.myinappbilling.email.viewmodel.EmailViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Activity for displaying the history of sent and draft emails.
 */
public class EmailHistoryActivity extends AppCompatActivity {

    private ListView emailListView;
    private SearchView searchView;
    private EmailViewModel emailViewModel;
    private ArrayAdapter<String> emailAdapter;
    private List<EmailMessage> emailHistoryList = new ArrayList<>();
    private List<String> emailSummaries = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_history);

        initViews();
        initViewModel();
        setupListViewListener();
        setupSearchViewListener();
    }

    private void initViews() {
        emailListView = findViewById(R.id.listViewEmails);
        searchView = findViewById(R.id.searchViewEmails);
        emailAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        emailListView.setAdapter(emailAdapter);
    }

    private void initViewModel() {
        emailViewModel = new ViewModelProvider(this).get(EmailViewModel.class);

        emailViewModel.getEmailHistory().observe(this, emailMessages -> {
            emailHistoryList.clear();
            emailHistoryList.addAll(emailMessages);
            updateEmailList();
        });
    }

    private void updateEmailList() {
        emailSummaries.clear();
        for (EmailMessage message : emailHistoryList) {
            emailSummaries.add(formatEmailSummary(message));
        }
        refreshAdapter(emailSummaries);
    }

    private String formatEmailSummary(EmailMessage message) {
        return String.format("%s | To: %s", message.getSubject(), message.getRecipient());
    }

    private void refreshAdapter(List<String> list) {
        emailAdapter.clear();
        emailAdapter.addAll(list);
        emailAdapter.notifyDataSetChanged();
    }

    private void setupListViewListener() {
        emailListView.setOnItemClickListener((parent, view, position, id) -> {
            EmailMessage selectedEmail = emailHistoryList.get(position);
            openEmailDetail(selectedEmail);
        });

        emailListView.setOnItemLongClickListener((parent, view, position, id) -> {
            deleteEmail(position);
            return true;
        });
    }

    private void openEmailDetail(EmailMessage email) {
        Intent intent = new Intent(this, EmailStatusActivity.class);
        intent.putExtra("email_subject", email.getSubject());
        intent.putExtra("email_recipient", email.getRecipient());
        intent.putExtra("email_body", email.getBody());
        startActivity(intent);
    }

    private void deleteEmail(int position) {
        EmailMessage emailToDelete = emailHistoryList.get(position);
        emailViewModel.deleteEmail(emailToDelete);
        Toast.makeText(this, "Email deleted", Toast.LENGTH_SHORT).show();
    }

    private void setupSearchViewListener() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterEmailList(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterEmailList(newText);
                return true;
            }
        });
    }

    private void filterEmailList(String query) {
        List<String> filteredList = emailSummaries.stream()
                .filter(summary -> summary.toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
        refreshAdapter(filteredList);
    }
}