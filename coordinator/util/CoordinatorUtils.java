package com.myinappbilling.coordinator.util;

import com.myinappbilling.coordinator.model.Coordinator;
import com.myinappbilling.coordinator.model.CoordinatorAssignment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CoordinatorUtils {

    /**
     * Filters coordinators based on active status.
     *
     * @param coordinators list of coordinators
     * @param isActive filter by active or inactive
     * @return filtered list
     */
    public static List<Coordinator> filterByActiveStatus(List<Coordinator> coordinators, boolean isActive) {
        return coordinators.stream()
                .filter(coordinator -> coordinator.isActive() == isActive)
                .collect(Collectors.toList());
    }

    /**
     * Finds assignments for a specific coordinator ID.
     *
     * @param assignments list of all assignments
     * @param coordinatorId coordinator ID to match
     * @return filtered list of assignments
     */
    public static List<CoordinatorAssignment> findAssignmentsByCoordinatorId(List<CoordinatorAssignment> assignments, String coordinatorId) {
        return assignments.stream()
                .filter(a -> a.getCoordinatorId().equals(coordinatorId))
                .collect(Collectors.toList());
    }

    /**
     * Converts a list of Coordinators into a list of their names.
     *
     * @param coordinators list of Coordinator objects
     * @return list of names
     */
    public static List<String> getCoordinatorNames(List<Coordinator> coordinators) {
        List<String> names = new ArrayList<>();
        for (Coordinator c : coordinators) {
            names.add(c.getName());
        }
        return names;
    }

    /**
     * Checks if a coordinator has any assignments.
     *
     * @param coordinatorId ID of the coordinator
     * @param assignments list of all assignments
     * @return true if assigned, false otherwise
     */
    public static boolean hasAssignments(String coordinatorId, List<CoordinatorAssignment> assignments) {
        return assignments.stream()
                .anyMatch(a -> a.getCoordinatorId().equals(coordinatorId));
    }

    /**
     * Sorts a list of coordinators by name alphabetically.
     *
     * @param coordinators list of Coordinator objects
     * @return sorted list
     */
    public static List<Coordinator> sortCoordinatorsByName(List<Coordinator> coordinators) {
        return coordinators.stream()
                .sorted(Comparator.comparing(Coordinator::getName))
                .collect(Collectors.toList());
    }

    /**
     * Gets a coordinator by ID from a list.
     *
     * @param coordinators list of coordinators
     * @param id coordinator ID
     * @return Optional of Coordinator
     */
    public static Optional<Coordinator> getCoordinatorById(List<Coordinator> coordinators, String id) {
        return coordinators.stream()
                .filter(coordinator -> coordinator.getId().equals(id))
                .findFirst();
    }

    /**
     * Groups assignments by coordinator ID.
     *
     * @param assignments list of assignments
     * @return map of coordinator ID to their assignments
     */
    public static java.util.Map<String, List<CoordinatorAssignment>> groupAssignmentsByCoordinator(List<CoordinatorAssignment> assignments) {
        return assignments.stream()
                .collect(Collectors.groupingBy(CoordinatorAssignment::getCoordinatorId));
    }
}
