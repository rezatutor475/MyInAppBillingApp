package com.myinappbilling.financial.repository;

import com.myinappbilling.financial.model.RefundRequest;
import com.myinappbilling.financial.model.RefundStatus;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Repository class for managing refund requests.
 */
public class RefundRepository {

    private final Map<String, RefundRequest> refundDatabase = new HashMap<>();

    /**
     * Adds a refund request to the repository.
     * @param refundRequest The refund request to add.
     */
    public void addRefundRequest(RefundRequest refundRequest) {
        if (refundRequest == null || refundRequest.getRequestId() == null) {
            throw new IllegalArgumentException("RefundRequest or Request ID cannot be null");
        }
        refundDatabase.put(refundRequest.getRequestId(), refundRequest);
    }

    /**
     * Retrieves a refund request by ID.
     * @param requestId The ID of the refund request.
     * @return Optional of RefundRequest
     */
    public Optional<RefundRequest> getRefundRequestById(String requestId) {
        return Optional.ofNullable(refundDatabase.get(requestId));
    }

    /**
     * Retrieves all refund requests.
     * @return List of all refund requests.
     */
    public List<RefundRequest> getAllRefundRequests() {
        return new ArrayList<>(refundDatabase.values());
    }

    /**
     * Updates an existing refund request.
     * @param refundRequest The refund request to update.
     */
    public void updateRefundRequest(RefundRequest refundRequest) {
        if (!refundDatabase.containsKey(refundRequest.getRequestId())) {
            throw new IllegalArgumentException("Refund request with ID " + refundRequest.getRequestId() + " does not exist.");
        }
        refundDatabase.put(refundRequest.getRequestId(), refundRequest);
    }

    /**
     * Deletes a refund request by ID.
     * @param requestId The ID of the refund request to delete.
     * @return True if deleted, false if not found.
     */
    public boolean deleteRefundRequest(String requestId) {
        return refundDatabase.remove(requestId) != null;
    }

    /**
     * Retrieves refund requests by user ID.
     * @param userId The user ID to filter by.
     * @return List of refund requests for the user.
     */
    public List<RefundRequest> getRefundRequestsByUserId(String userId) {
        return refundDatabase.values().stream()
                .filter(r -> userId.equals(r.getUserId()))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves refund requests by status.
     * @param status The refund status to filter by.
     * @return List of refund requests with the given status.
     */
    public List<RefundRequest> getRefundRequestsByStatus(RefundStatus status) {
        return refundDatabase.values().stream()
                .filter(r -> r.getStatus() == status)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves refund requests submitted within a specific date range.
     * @param fromDate Start date (inclusive).
     * @param toDate End date (inclusive).
     * @return List of refund requests within the date range.
     */
    public List<RefundRequest> getRefundRequestsByDateRange(Date fromDate, Date toDate) {
        return refundDatabase.values().stream()
                .filter(r -> !r.getRequestDate().before(fromDate) && !r.getRequestDate().after(toDate))
                .collect(Collectors.toList());
    }

    /**
     * Counts the number of refund requests by status.
     * @param status The refund status.
     * @return The count of refund requests with the specified status.
     */
    public long countRefundRequestsByStatus(RefundStatus status) {
        return refundDatabase.values().stream()
                .filter(r -> r.getStatus() == status)
                .count();
    }

    /**
     * Clears all refund requests (for testing or reset purposes).
     */
    public void clearAllRefundRequests() {
        refundDatabase.clear();
    }
}
