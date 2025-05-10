package com.myinappbilling.apiconfig.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.myinappbilling.R;
import com.myinappbilling.apiconfig.model.ApiConfiguration;
import com.myinappbilling.apiconfig.viewmodel.ApiConfigViewModel;

/**
 * Activity to display, edit, and manage details of a single API Configuration.
 */
public class ApiConfigDetailsActivity extends AppCompatActivity {

    private EditText editName, editBaseUrl, editApiKey;
    private Button saveButton, deleteButton;
    private ApiConfigViewModel viewModel;
    private ApiConfiguration currentConfig;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_config_details);

        initializeViews();
        setupViewModel();
        setupListeners();
        loadConfiguration();
    }

    private void initializeViews() {
        editName = findViewById(R.id.edit_name);
        editBaseUrl = findViewById(R.id.edit_base_url);
        editApiKey = findViewById(R.id.edit_api_key);
        saveButton = findViewById(R.id.button_save);
        deleteButton = findViewById(R.id.button_delete);
    }

    private void setupViewModel() {
        viewModel = new ApiConfigViewModel();
    }

    private void setupListeners() {
        saveButton.setOnClickListener(v -> saveConfiguration());
        deleteButton.setOnClickListener(v -> deleteConfiguration());
    }

    private void loadConfiguration() {
        if (getIntent() != null && getIntent().hasExtra("config")) {
            currentConfig = (ApiConfiguration) getIntent().getSerializableExtra("config");
            if (currentConfig != null) {
                populateFields(currentConfig);
            }
        }
    }

    private void populateFields(ApiConfiguration config) {
        editName.setText(config.getName());
        editBaseUrl.setText(config.getBaseUrl());
        editApiKey.setText(config.getApiKey());
    }

    private void saveConfiguration() {
        String name = editName.getText().toString().trim();
        String baseUrl = editBaseUrl.getText().toString().trim();
        String apiKey = editApiKey.getText().toString().trim();

        if (name.isEmpty() || baseUrl.isEmpty() || apiKey.isEmpty()) {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
            return;
        }

        if (currentConfig == null) {
            currentConfig = new ApiConfiguration();
        }

        currentConfig.setName(name);
        currentConfig.setBaseUrl(baseUrl);
        currentConfig.setApiKey(apiKey);

        if (getIntent().hasExtra("config")) {
            viewModel.updateConfiguration(currentConfig);
            Toast.makeText(this, "Configuration updated", Toast.LENGTH_SHORT).show();
        } else {
            viewModel.addConfiguration(currentConfig);
            Toast.makeText(this, "Configuration added", Toast.LENGTH_SHORT).show();
        }

        finish();
    }

    private void deleteConfiguration() {
        if (currentConfig != null) {
            viewModel.deleteConfiguration(currentConfig);
            Toast.makeText(this, "Configuration deleted", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_api_config_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            refreshFields();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void refreshFields() {
        if (currentConfig != null) {
            populateFields(currentConfig);
            Toast.makeText(this, "Fields refreshed", Toast.LENGTH_SHORT).show();
        }
    }
}
