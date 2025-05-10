package com.myinappbilling.coordinator.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.myinappbilling.R;
import com.myinappbilling.coordinator.model.Coordinator;
import com.myinappbilling.coordinator.viewmodel.CoordinatorViewModel;
import com.myinappbilling.coordinator.service.CoordinatorService;

public class CoordinatorDetailsActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText roleEditText;
    private Button saveButton;

    private CoordinatorViewModel viewModel;
    private Coordinator existingCoordinator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_details);

        nameEditText = findViewById(R.id.editTextCoordinatorName);
        roleEditText = findViewById(R.id.editTextCoordinatorRole);
        saveButton = findViewById(R.id.buttonSaveCoordinator);

        CoordinatorService coordinatorService = new CoordinatorService();
        viewModel = new ViewModelProvider(this, new CoordinatorViewModelFactory(coordinatorService)).get(CoordinatorViewModel.class);

        long coordinatorId = getIntent().getLongExtra("coordinator_id", -1);
        if (coordinatorId != -1) {
            viewModel.loadCoordinatorById(coordinatorId);
            observeCoordinator();
        }

        saveButton.setOnClickListener(v -> saveCoordinator());
    }

    private void observeCoordinator() {
        viewModel.getSelectedCoordinator().observe(this, coordinator -> {
            if (coordinator != null) {
                existingCoordinator = coordinator;
                nameEditText.setText(coordinator.getName());
                roleEditText.setText(coordinator.getRole());
            }
        });
    }

    private void saveCoordinator() {
        String name = nameEditText.getText().toString().trim();
        String role = roleEditText.getText().toString().trim();

        if (name.isEmpty() || role.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (existingCoordinator == null) {
            Coordinator newCoordinator = new Coordinator(name, role);
            viewModel.addCoordinator(newCoordinator);
        } else {
            existingCoordinator.setName(name);
            existingCoordinator.setRole(role);
            viewModel.updateCoordinator(existingCoordinator);
        }

        Toast.makeText(this, "Coordinator saved successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
} 
