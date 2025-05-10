package com.myinappbilling.databasesetup.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.myinappbilling.R;
import com.myinappbilling.databasesetup.model.DatabaseConfig;
import com.myinappbilling.databasesetup.service.DatabaseInitializerService;
import com.myinappbilling.databasesetup.validator.DatabaseConfigValidator;
import com.myinappbilling.databasesetup.viewmodel.DatabaseSetupViewModel;

public class DatabaseSetupActivity extends ComponentActivity {

    private DatabaseSetupViewModel viewModel;
    private EditText dbNameInput;
    private EditText dbVersionInput;
    private Button setupButton;
    private Button clearButton;
    private Button testConnectionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_setup);

        dbNameInput = findViewById(R.id.editTextDatabaseName);
        dbVersionInput = findViewById(R.id.editTextDatabaseVersion);
        setupButton = findViewById(R.id.buttonSetupDatabase);
        clearButton = findViewById(R.id.buttonClearForm);
        testConnectionButton = findViewById(R.id.buttonTestConnection);

        viewModel = new ViewModelProvider(
                this,
                new ViewModelProvider.NewInstanceFactory()
        ).get(DatabaseSetupViewModel.class);

        viewModel.getSetupStatus().observe(this, status ->
                Toast.makeText(this, status, Toast.LENGTH_SHORT).show()
        );

        setupButton.setOnClickListener(v -> {
            String dbName = dbNameInput.getText().toString();
            String dbVersionText = dbVersionInput.getText().toString();

            if (dbName.isEmpty() || dbVersionText.isEmpty()) {
                Toast.makeText(this, "Please enter both database name and version.", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                int dbVersion = Integer.parseInt(dbVersionText);
                DatabaseConfig config = new DatabaseConfig(dbName, dbVersion);
                viewModel.updateDatabaseConfig(config);
                viewModel.initializeDatabase();
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Database version must be a number.", Toast.LENGTH_SHORT).show();
            }
        });

        clearButton.setOnClickListener(v -> {
            dbNameInput.setText("");
            dbVersionInput.setText("");
            Toast.makeText(this, "Form cleared.", Toast.LENGTH_SHORT).show();
        });

        testConnectionButton.setOnClickListener(v -> {
            String dbName = dbNameInput.getText().toString();
            String dbVersionText = dbVersionInput.getText().toString();

            if (dbName.isEmpty() || dbVersionText.isEmpty()) {
                Toast.makeText(this, "Enter values to test connection.", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                int dbVersion = Integer.parseInt(dbVersionText);
                DatabaseConfig config = new DatabaseConfig(dbName, dbVersion);
                boolean testResult = viewModel.testConnection(config);
                if (testResult) {
                    Toast.makeText(this, "Connection successful.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Connection failed.", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid version format.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
