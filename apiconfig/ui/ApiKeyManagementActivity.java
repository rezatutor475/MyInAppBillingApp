package com.myinappbilling.apiconfig.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.myinappbilling.R;
import com.myinappbilling.apiconfig.model.ApiKey;
import com.myinappbilling.apiconfig.viewmodel.ApiConfigViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity to manage API Keys, including viewing, adding, deleting, and rotating keys.
 */
public class ApiKeyManagementActivity extends AppCompatActivity {

    private ListView apiKeyListView;
    private ArrayAdapter<String> adapter;
    private List<ApiKey> apiKeys = new ArrayList<>();
    private ApiConfigViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_key_management);

        apiKeyListView = findViewById(R.id.list_api_keys);
        viewModel = new ApiConfigViewModel();

        loadApiKeys();

        apiKeyListView.setOnItemLongClickListener((parent, view, position, id) -> {
            confirmDeleteApiKey(position);
            return true;
        });
    }

    private void loadApiKeys() {
        apiKeys = viewModel.getAllApiKeys();
        List<String> keyNames = new ArrayList<>();
        for (ApiKey key : apiKeys) {
            keyNames.add(key.getKeyName());
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, keyNames);
        apiKeyListView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_api_key_management, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add_key) {
            showAddApiKeyDialog();
            return true;
        } else if (id == R.id.action_rotate_keys) {
            rotateApiKeys();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showAddApiKeyDialog() {
        EditText input = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New API Key");
        builder.setView(input);
        builder.setPositiveButton("Add", (dialog, which) -> {
            String keyName = input.getText().toString().trim();
            if (!keyName.isEmpty()) {
                ApiKey newKey = new ApiKey();
                newKey.setKeyName(keyName);
                viewModel.addApiKey(newKey);
                Toast.makeText(this, "API Key added", Toast.LENGTH_SHORT).show();
                loadApiKeys();
            } else {
                Toast.makeText(this, "Key name cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void confirmDeleteApiKey(int position) {
        new AlertDialog.Builder(this)
                .setTitle("Delete API Key")
                .setMessage("Are you sure you want to delete this API key?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    viewModel.deleteApiKey(apiKeys.get(position));
                    Toast.makeText(this, "API Key deleted", Toast.LENGTH_SHORT).show();
                    loadApiKeys();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void rotateApiKeys() {
        viewModel.rotateApiKeys();
        Toast.makeText(this, "API Keys rotated successfully", Toast.LENGTH_SHORT).show();
        loadApiKeys();
    }
}
