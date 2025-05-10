package com.myinappbilling.coordinator.repository;

import com.myinappbilling.coordinator.model.Coordinator;
import com.myinappbilling.coordinator.model.CoordinatorAssignment;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class CoordinatorRepository {

    private final Map<String, Coordinator> coordinatorMap = new ConcurrentHashMap<>();
    private final Map<String, CoordinatorAssignment> assignmentMap = new ConcurrentHashMap<>();

    // Coordinator CRUD operations
    public void saveCoordinator(Coordinator coordinator) {
        coordinatorMap.put(coordinator.getCoordinatorId(), coordinator);
    }

    public Optional<Coordinator> findCoordinatorById(String coordinatorId) {
        return Optional.ofNullable(coordinatorMap.get(coordinatorId));
    }

    public List<Coordinator> findAllCoordinators() {
        return new ArrayList<>(coordinatorMap.values());
    }

    public boolean existsCoordinatorById(String coordinatorId) {
        return coordinatorMap.containsKey(coordinatorId);
    }

    public void deleteCoordinator(String coordinatorId) {
        coordinatorMap.remove(coordinatorId);
    }

    // CoordinatorAssignment CRUD operations
    public void saveAssignment(CoordinatorAssignment assignment) {
        assignmentMap.put(assignment.getAssignmentId(), assignment);
    }

    public Optional<CoordinatorAssignment> findAssignmentById(String assignmentId) {
        return Optional.ofNullable(assignmentMap.get(assignmentId));
    }

    public List<CoordinatorAssignment> findAssignmentsByCoordinatorId(String coordinatorId) {
        List<CoordinatorAssignment> assignments = new ArrayList<>();
        for (CoordinatorAssignment assignment : assignmentMap.values()) {
            if (assignment.getCoordinatorId().equals(coordinatorId)) {
                assignments.add(assignment);
            }
        }
        return assignments;
    }

    public List<CoordinatorAssignment> findAllAssignments() {
        return new ArrayList<>(assignmentMap.values());
    }

    public boolean existsAssignmentById(String assignmentId) {
        return assignmentMap.containsKey(assignmentId);
    }

    public void deleteAssignment(String assignmentId) {
        assignmentMap.remove(assignmentId);
    }

    public List<CoordinatorAssignment> findAssignmentsByTaskId(String taskId) {
        List<CoordinatorAssignment> results = new ArrayList<>();
        for (CoordinatorAssignment assignment : assignmentMap.values()) {
            if (assignment.getTaskId().equals(taskId)) {
                results.add(assignment);
            }
        }
        return results;
    }

    public boolean isCoordinatorAssignedToTask(String coordinatorId, String taskId) {
        return assignmentMap.values().stream()
                .anyMatch(a -> a.getCoordinatorId().equals(coordinatorId) && a.getTaskId().equals(taskId));
    }

    // Additional utility functions
    public Map<String, Long> countAssignmentsPerCoordinator() {
        Map<String, Long> countMap = new HashMap<>();
        for (CoordinatorAssignment assignment : assignmentMap.values()) {
            countMap.put(assignment.getCoordinatorId(), countMap.getOrDefault(assignment.getCoordinatorId(), 0L) + 1);
        }
        return countMap;
    }

    public List<Coordinator> findUnassignedCoordinators() {
        Set<String> assignedIds = new HashSet<>();
        for (CoordinatorAssignment assignment : assignmentMap.values()) {
            assignedIds.add(assignment.getCoordinatorId());
        }
        List<Coordinator> unassigned = new ArrayList<>();
        for (Coordinator coordinator : coordinatorMap.values()) {
            if (!assignedIds.contains(coordinator.getCoordinatorId())) {
                unassigned.add(coordinator);
            }
        }
        return unassigned;
    }
} 
