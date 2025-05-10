package com.myinappbilling.coordinator.service;

import com.myinappbilling.coordinator.model.CoordinatorAssignment;
import com.myinappbilling.coordinator.repository.CoordinatorRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AssignmentService {

    private final CoordinatorRepository repository;

    public AssignmentService(CoordinatorRepository repository) {
        this.repository = repository;
    }

    public CoordinatorAssignment assignCoordinatorToTask(CoordinatorAssignment assignment) {
        assignment.setAssignmentId(UUID.randomUUID().toString());
        repository.saveAssignment(assignment);
        return assignment;
    }

    public Optional<CoordinatorAssignment> getAssignmentById(String assignmentId) {
        return repository.findAssignmentById(assignmentId);
    }

    public List<CoordinatorAssignment> getAssignmentsForCoordinator(String coordinatorId) {
        return repository.findAssignmentsByCoordinatorId(coordinatorId);
    }

    public List<CoordinatorAssignment> getAllAssignments() {
        return repository.findAllAssignments();
    }

    public CoordinatorAssignment updateAssignment(CoordinatorAssignment assignment) {
        if (repository.existsAssignmentById(assignment.getAssignmentId())) {
            repository.saveAssignment(assignment);
            return assignment;
        }
        throw new IllegalArgumentException("Assignment not found with ID: " + assignment.getAssignmentId());
    }

    public boolean deleteAssignment(String assignmentId) {
        if (repository.existsAssignmentById(assignmentId)) {
            repository.deleteAssignment(assignmentId);
            return true;
        }
        return false;
    }

    public List<CoordinatorAssignment> getAssignmentsByTaskId(String taskId) {
        return repository.findAssignmentsByTaskId(taskId);
    }

    public boolean isCoordinatorAssignedToTask(String coordinatorId, String taskId) {
        return repository.findAssignmentsByCoordinatorId(coordinatorId).stream()
                .anyMatch(assignment -> assignment.getTaskId().equals(taskId));
    }
} 
