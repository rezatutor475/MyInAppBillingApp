package com.myinappbilling.financial.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myinappbilling.R;
import com.myinappbilling.financial.model.Transaction;
import com.myinappbilling.financial.viewmodel.FinancialViewModel;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Activity to display transaction history.
 */
public class TransactionHistoryActivity extends AppCompatActivity {

    private FinancialViewModel financialViewModel;
    private RecyclerView transactionRecyclerView;
    private TransactionAdapter transactionAdapter;
    private ProgressBar loadingIndicator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);

        transactionRecyclerView = findViewById(R.id.transaction_recycler_view);
        loadingIndicator = findViewById(R.id.loading_indicator);

        transactionRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        transactionAdapter = new TransactionAdapter();
        transactionRecyclerView.setAdapter(transactionAdapter);

        financialViewModel = new ViewModelProvider(this).get(FinancialViewModel.class);

        observeViewModel();
        fetchData();
    }

    private void observeViewModel() {
        financialViewModel.getTransactions().observe(this, this::displayTransactions);
    }

    private void fetchData() {
        loadingIndicator.setVisibility(View.VISIBLE);
        financialViewModel.refreshData();
    }

    private void displayTransactions(List<Transaction> transactions) {
        loadingIndicator.setVisibility(View.GONE);
        if (transactions == null || transactions.isEmpty()) {
            Toast.makeText(this, "No transactions found.", Toast.LENGTH_SHORT).show();
        } else {
            sortTransactionsByDateDescending(transactions);
            transactionAdapter.setTransactions(transactions);
        }
    }

    /**
     * Sort transactions by date in descending order (most recent first).
     * @param transactions The list of transactions to sort.
     */
    private void sortTransactionsByDateDescending(List<Transaction> transactions) {
        Collections.sort(transactions, new Comparator<Transaction>() {
            @Override
            public int compare(Transaction t1, Transaction t2) {
                return t2.getDate().compareTo(t1.getDate());
            }
        });
    }

    /**
     * Refresh transaction data manually, could be triggered from UI like pull-to-refresh.
     */
    public void manualRefresh() {
        fetchData();
    }
}

// Note: Ensure activity_transaction_history.xml contains a ProgressBar with id 'loading_indicator'.
// TransactionAdapter should be implemented to bind the list of transactions to the RecyclerView.
