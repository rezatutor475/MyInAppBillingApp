package com.myinappbilling.databasesetup.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.myinappbilling.R;
import com.myinappbilling.databasesetup.viewmodel.DatabaseSetupViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TableManagementActivity extends ComponentActivity {

    private ListView tableListView;
    private Button refreshButton;
    private Button dropAllTablesButton;
    private SearchView tableSearchView;
    private DatabaseSetupViewModel viewModel;
    private List<String> allTables = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_management);

        tableListView = findViewById(R.id.listViewTables);
        refreshButton = findViewById(R.id.buttonRefreshTables);
        dropAllTablesButton = findViewById(R.id.buttonDropAllTables);
        tableSearchView = findViewById(R.id.searchViewTables);

        viewModel = new ViewModelProvider(this).get(DatabaseSetupViewModel.class);

        viewModel.getTables().observe(this, tables -> {
            allTables = tables != null ? tables : new ArrayList<>();
            updateTableList(allTables);
        });

        viewModel.getDropAllTablesResult().observe(this, result -> {
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            viewModel.loadTables();
        });

        refreshButton.setOnClickListener(v -> viewModel.loadTables());

        dropAllTablesButton.setOnClickListener(v -> viewModel.dropAllTables());

        tableSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterTableList(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterTableList(newText);
                return true;
            }
        });

        viewModel.loadTables();
    }

    private void updateTableList(List<String> tables) {
        if (tables == null || tables.isEmpty()) {
            Toast.makeText(this, "No tables found.", Toast.LENGTH_SHORT).show();
            tableListView.setAdapter(null);
            return;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                tables
        );
        tableListView.setAdapter(adapter);
    }

    private void filterTableList(String query) {
        List<String> filtered = allTables.stream()
                .filter(table -> table.toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
        updateTableList(filtered);
    }
}
