package com.myinappbilling.databasesetup.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.myinappbilling.R;
import com.myinappbilling.databasesetup.viewmodel.DatabaseSetupViewModel;

import java.util.Collections;
import java.util.List;

public class MigrationHistoryActivity extends ComponentActivity {

    private ListView migrationListView;
    private Button refreshButton;
    private Button clearHistoryButton;
    private DatabaseSetupViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_migration_history);

        migrationListView = findViewById(R.id.listViewMigrationHistory);
        refreshButton = findViewById(R.id.buttonRefreshHistory);
        clearHistoryButton = findViewById(R.id.buttonClearHistory);

        viewModel = new ViewModelProvider(this).get(DatabaseSetupViewModel.class);

        viewModel.getMigrationHistory().observe(this, this::updateMigrationHistory);
        viewModel.getHistoryClearStatus().observe(this, this::showClearStatus);

        refreshButton.setOnClickListener(v -> viewModel.loadMigrationHistory());
        clearHistoryButton.setOnClickListener(v -> viewModel.clearMigrationHistory());

        viewModel.loadMigrationHistory();
    }

    private void updateMigrationHistory(List<String> migrations) {
        if (migrations == null || migrations.isEmpty()) {
            Toast.makeText(this, "No migration history found.", Toast.LENGTH_SHORT).show();
            migrationListView.setAdapter(null);
            return;
        }

        Collections.reverse(migrations); // show most recent first
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                migrations
        );
        migrationListView.setAdapter(adapter);
    }

    private void showClearStatus(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        viewModel.loadMigrationHistory();
    }
}
