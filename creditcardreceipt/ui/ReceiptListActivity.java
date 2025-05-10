package com.myinappbilling.creditcardreceipt.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myinappbilling.creditcardreceipt.R;
import com.myinappbilling.creditcardreceipt.model.Receipt;
import com.myinappbilling.creditcardreceipt.viewmodel.ReceiptViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Activity to display the list of receipts with search, sort, and grouping features.
 */
public class ReceiptListActivity extends AppCompatActivity implements ReceiptListAdapter.OnReceiptClickListener {

    private ReceiptViewModel receiptViewModel;
    private RecyclerView recyclerView;
    private ReceiptListAdapter adapter;
    private List<Receipt> receiptList = new ArrayList<>();
    private List<Receipt> displayedList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_list);

        initViews();
        setupViewModel();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recycler_receipts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ReceiptListAdapter(displayedList, this);
        recyclerView.setAdapter(adapter);
    }

    private void setupViewModel() {
        receiptViewModel = new ViewModelProvider(this).get(ReceiptViewModel.class);
        receiptViewModel.getAllReceipts().observe(this, receipts -> {
            if (receipts != null) {
                receiptList.clear();
                receiptList.addAll(receipts);
                displayedList.clear();
                displayedList.addAll(receipts);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onReceiptClick(Receipt receipt) {
        receiptViewModel.setSelectedReceipt(receipt);
        startActivity(new Intent(this, ReceiptDetailsActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_receipt_list, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search Receipts");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterReceipts(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterReceipts(newText);
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort_date:
                sortByDate();
                return true;
            case R.id.action_sort_amount:
                sortByAmount();
                return true;
            case R.id.action_group_merchant:
                groupByMerchant();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void filterReceipts(String query) {
        List<Receipt> filteredList = new ArrayList<>();
        for (Receipt receipt : receiptList) {
            if (receipt.getMerchantName().toLowerCase().contains(query.toLowerCase()) ||
                (receipt.getTransactionId() != null && receipt.getTransactionId().toLowerCase().contains(query.toLowerCase()))) {
                filteredList.add(receipt);
            }
        }
        updateDisplayedList(filteredList);
    }

    private void sortByDate() {
        Collections.sort(displayedList, Comparator.comparing(Receipt::getTransactionDate));
        adapter.notifyDataSetChanged();
    }

    private void sortByAmount() {
        Collections.sort(displayedList, Comparator.comparingDouble(Receipt::getTotalAmount));
        adapter.notifyDataSetChanged();
    }

    private void groupByMerchant() {
        Collections.sort(displayedList, Comparator.comparing(Receipt::getMerchantName));
        adapter.notifyDataSetChanged();
    }

    private void updateDisplayedList(List<Receipt> newList) {
        displayedList.clear();
        displayedList.addAll(newList);
        adapter.notifyDataSetChanged();
    }
}
