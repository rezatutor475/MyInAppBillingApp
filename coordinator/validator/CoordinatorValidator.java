package com.myinappbilling.coordinator.validator;

import com.myinappbilling.coordinator.model.Coordinator;
import com.myinappbilling.coordinator.model.CoordinatorAssignment;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CoordinatorValidator {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public static List<String> validateCoordinator(Coordinator coordinator) {
        List<String> errors = new ArrayList<>();

        if (coordinator == null) {
            errors.add("Coordinator object must not be null.");
            return errors;
        }

        if (coordinator.getCoordinatorId() == null || coordinator.getCoordinatorId().isEmpty()) {
            errors.add("Coordinator ID must not be empty.");
        }
        if (coordinator.getName() == null || coordinator.getName().trim().isEmpty()) {
            errors.add("Coordinator name must not be empty.");
        }
        if (coordinator.getEmail() == null || !EMAIL_PATTERN.matcher(coordinator.getEmail()).matches()) {
            errors.add("Invalid email format.");
        }
        if (coordinator.getRole() == null) {
            errors.add("Coordinator role must be specified.");
        }

        return errors;
    }

    public static List<String> validateAssignment(CoordinatorAssignment assignment) {
        List<String> errors = new ArrayList<>();

        if (assignment == null) {
            errors.add("Assignment object must not be null.");
            return errors;
        }

        if (assignment.getAssignmentId() == null || assignment.getAssignmentId().isEmpty()) {
            errors.add("Assignment ID must not be empty.");
        }
        if (assignment.getCoordinatorId() == null || assignment.getCoordinatorId().isEmpty()) {
            errors.add("Coordinator ID must not be empty for an assignment.");
        }
        if (assignment.getTaskId() == null || assignment.getTaskId().isEmpty()) {
            errors.add("Task ID must not be empty for an assignment.");
        }
        if (assignment.getStartDate() != null && assignment.getEndDate() != null &&
                assignment.getStartDate().after(assignment.getEndDate())) {
            errors.add("Start date must be before end date.");
        }

        return errors;
    }
} 
