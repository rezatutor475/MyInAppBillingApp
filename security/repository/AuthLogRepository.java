package com.myinappbilling.security.repository;

import com.myinappbilling.security.model.AuthAttempt;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Repository class for managing authentication attempt logs.
 */
public class AuthLogRepository {

    private final List<AuthAttempt> authLogs = new ArrayList<>();

    /**
     * Saves an authentication attempt log.
     *
     * @param attempt AuthAttempt object to save
     */
    public void save(AuthAttempt attempt) {
        authLogs.add(attempt);
    }

    /**
     * Retrieves all authentication attempts.
     *
     * @return List of AuthAttempt objects
     */
    public List<AuthAttempt> getAllAttempts() {
        return new ArrayList<>(authLogs);
    }

    /**
     * Retrieves the latest authentication attempt for a specific user.
     *
     * @param userId The user ID to filter logs
     * @return Optional containing the latest AuthAttempt for the user
     */
    public Optional<AuthAttempt> getLatestAttemptByUserId(String userId) {
        return authLogs.stream()
                .filter(attempt -> attempt.getUserId().equals(userId))
                .reduce((first, second) -> second);
    }

    /**
     * Retrieves authentication attempts for a specific user.
     *
     * @param userId The user ID to filter logs
     * @return List of AuthAttempt objects for the user
     */
    public List<AuthAttempt> getAttemptsByUserId(String userId) {
        return authLogs.stream()
                .filter(attempt -> attempt.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves authentication attempts within a time range.
     *
     * @param from Start of time range
     * @param to End of time range
     * @return List of AuthAttempt objects in the specified range
     */
    public List<AuthAttempt> getAttemptsBetween(LocalDateTime from, LocalDateTime to) {
        return authLogs.stream()
                .filter(attempt -> !attempt.getTimestamp().isBefore(from) && !attempt.getTimestamp().isAfter(to))
                .collect(Collectors.toList());
    }

    /**
     * Counts the number of failed attempts for a user.
     *
     * @param userId The user ID to count failures for
     * @return Number of failed attempts
     */
    public long countFailedAttempts(String userId) {
        return authLogs.stream()
                .filter(attempt -> attempt.getUserId().equals(userId) && !attempt.isSuccessful())
                .count();
    }

    /**
     * Clears all authentication attempt logs.
     */
    public void clearAllLogs() {
        authLogs.clear();
    }
}
