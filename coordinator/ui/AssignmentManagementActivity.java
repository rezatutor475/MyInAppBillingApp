package com.myinappbilling.coordinator.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.myinappbilling.R;
import com.myinappbilling.coordinator.model.Coordinator;
import com.myinappbilling.coordinator.model.CoordinatorAssignment;
import com.myinappbilling.coordinator.viewmodel.CoordinatorViewModel;
import com.myinappbilling.coordinator.service.CoordinatorService;

import java.util.List;

public class AssignmentManagementActivity extends AppCompatActivity {

    private Spinner coordinatorSpinner;
    private Spinner taskSpinner;
    private Button assignButton;
    private Button removeAssignmentButton;

    private CoordinatorViewModel viewModel;
    private List<Coordinator> coordinatorList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_management);

        coordinatorSpinner = findViewById(R.id.spinnerCoordinators);
        taskSpinner = findViewById(R.id.spinnerTasks);
        assignButton = findViewById(R.id.buttonAssign);
        removeAssignmentButton = findViewById(R.id.buttonRemoveAssignment);

        CoordinatorService coordinatorService = new CoordinatorService();
        viewModel = new ViewModelProvider(this, new CoordinatorViewModelFactory(coordinatorService)).get(CoordinatorViewModel.class);

        loadCoordinators();
        setupAssignButton();
        setupRemoveAssignmentButton();
    }

    private void loadCoordinators() {
        viewModel.getAllCoordinators().observe(this, coordinators -> {
            coordinatorList = coordinators;
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item,
                    coordinators.stream().map(Coordinator::getName).toArray(String[]::new));
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            coordinatorSpinner.setAdapter(adapter);
        });
    }

    private void setupAssignButton() {
        assignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int coordinatorPosition = coordinatorSpinner.getSelectedItemPosition();
                String task = taskSpinner.getSelectedItem().toString();

                if (coordinatorPosition < 0 || task.isEmpty()) {
                    Toast.makeText(AssignmentManagementActivity.this, "Please select both coordinator and task.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Coordinator selectedCoordinator = coordinatorList.get(coordinatorPosition);
                CoordinatorAssignment assignment = new CoordinatorAssignment(selectedCoordinator.getId(), task);

                viewModel.assignCoordinator(assignment);
                Toast.makeText(AssignmentManagementActivity.this, "Coordinator assigned successfully.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupRemoveAssignmentButton() {
        removeAssignmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int coordinatorPosition = coordinatorSpinner.getSelectedItemPosition();
                String task = taskSpinner.getSelectedItem().toString();

                if (coordinatorPosition < 0 || task.isEmpty()) {
                    Toast.makeText(AssignmentManagementActivity.this, "Please select both coordinator and task.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Coordinator selectedCoordinator = coordinatorList.get(coordinatorPosition);
                viewModel.removeAssignment(selectedCoordinator.getId(), task);
                Toast.makeText(AssignmentManagementActivity.this, "Assignment removed successfully.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
