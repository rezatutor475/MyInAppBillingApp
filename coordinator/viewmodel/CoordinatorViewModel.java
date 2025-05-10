package com.myinappbilling.coordinator.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.myinappbilling.coordinator.model.Coordinator;
import com.myinappbilling.coordinator.model.CoordinatorAssignment;
import com.myinappbilling.coordinator.service.CoordinatorService;
import com.myinappbilling.coordinator.service.AssignmentService;

import java.util.List;

public class CoordinatorViewModel extends ViewModel {

    private final CoordinatorService coordinatorService;
    private final AssignmentService assignmentService;

    private final MutableLiveData<List<Coordinator>> coordinatorList = new MutableLiveData<>();
    private final MutableLiveData<List<CoordinatorAssignment>> assignmentList = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public CoordinatorViewModel(CoordinatorService coordinatorService, AssignmentService assignmentService) {
        this.coordinatorService = coordinatorService;
        this.assignmentService = assignmentService;
        loadCoordinators();
        loadAssignments();
    }

    public LiveData<List<Coordinator>> getCoordinatorList() {
        return coordinatorList;
    }

    public LiveData<List<CoordinatorAssignment>> getAssignmentList() {
        return assignmentList;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void loadCoordinators() {
        try {
            List<Coordinator> coordinators = coordinatorService.getAllCoordinators();
            coordinatorList.setValue(coordinators);
        } catch (Exception e) {
            errorMessage.setValue("Failed to load coordinators: " + e.getMessage());
        }
    }

    public void loadAssignments() {
        try {
            List<CoordinatorAssignment> assignments = assignmentService.getAllAssignments();
            assignmentList.setValue(assignments);
        } catch (Exception e) {
            errorMessage.setValue("Failed to load assignments: " + e.getMessage());
        }
    }

    public void addCoordinator(Coordinator coordinator) {
        try {
            coordinatorService.addCoordinator(coordinator);
            loadCoordinators();
        } catch (Exception e) {
            errorMessage.setValue("Failed to add coordinator: " + e.getMessage());
        }
    }

    public void deleteCoordinator(String coordinatorId) {
        try {
            coordinatorService.deleteCoordinator(coordinatorId);
            loadCoordinators();
        } catch (Exception e) {
            errorMessage.setValue("Failed to delete coordinator: " + e.getMessage());
        }
    }

    public void assignTask(CoordinatorAssignment assignment) {
        try {
            assignmentService.assignTask(assignment);
            loadAssignments();
        } catch (Exception e) {
            errorMessage.setValue("Failed to assign task: " + e.getMessage());
        }
    }

    public void removeAssignment(String assignmentId) {
        try {
            assignmentService.removeAssignment(assignmentId);
            loadAssignments();
        } catch (Exception e) {
            errorMessage.setValue("Failed to remove assignment: " + e.getMessage());
        }
    }
} 
