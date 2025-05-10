package com.myinappbilling.creditcardreceipt.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myinappbilling.creditcardreceipt.R;
import com.myinappbilling.creditcardreceipt.model.TransactionInfo;
import com.myinappbilling.creditcardreceipt.viewmodel.TransactionViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Activity to display the transaction history related to receipts with advanced features like search, sort, and filter.
 */
public class TransactionHistoryActivity extends AppCompatActivity implements TransactionListAdapter.OnTransactionClickListener {

    private TransactionViewModel transactionViewModel;
    private RecyclerView recyclerView;
    private TransactionListAdapter adapter;
    private List<TransactionInfo> transactionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);

        initViews();
        setupViewModel();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recycler_transactions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TransactionListAdapter(transactionList, this);
        recyclerView.setAdapter(adapter);
    }

    private void setupViewModel() {
        transactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);
        transactionViewModel.getAllTransactions().observe(this, transactions -> {
            if (transactions != null) {
                transactionList.clear();
                transactionList.addAll(transactions);
                adapter.updateList(transactionList);
            }
        });
    }

    @Override
    public void onTransactionClick(TransactionInfo transactionInfo) {
        transactionViewModel.setSelectedTransaction(transactionInfo);
        startActivity(new Intent(this, ReceiptDetailsActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_transaction_history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort_by_date:
                sortByDate();
                return true;
            case R.id.action_sort_by_amount:
                sortByAmount();
                return true;
            case R.id.action_sort_by_merchant:
                sortByMerchant();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void sortByDate() {
        Collections.sort(transactionList, Comparator.comparing(TransactionInfo::getTransactionDate));
        adapter.updateList(transactionList);
    }

    private void sortByAmount() {
        Collections.sort(transactionList, Comparator.comparing(TransactionInfo::getAmount));
        adapter.updateList(transactionList);
    }

    private void sortByMerchant() {
        Collections.sort(transactionList, Comparator.comparing(TransactionInfo::getMerchantName));
        adapter.updateList(transactionList);
    }
}
