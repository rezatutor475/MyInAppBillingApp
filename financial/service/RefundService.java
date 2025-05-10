package com.myinappbilling.financial.service;

import com.myinappbilling.financial.model.RefundRequest;
import com.myinappbilling.financial.model.RefundStatus;
import com.myinappbilling.financial.repository.RefundRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Service class for handling business logic related to refund requests.
 */
public class RefundService {

    private final RefundRepository refundRepository;

    public RefundService(RefundRepository refundRepository) {
        this.refundRepository = refundRepository;
    }

    /**
     * Submits a new refund request.
     *
     * @param refundRequest The refund request to be submitted.
     */
    public void submitRefundRequest(RefundRequest refundRequest) {
        refundRequest.setRequestDate(new Date());
        refundRequest.setStatus(RefundStatus.PENDING);
        refundRepository.addRefundRequest(refundRequest);
    }

    /**
     * Retrieves a refund request by ID.
     *
     * @param requestId The ID of the refund request.
     * @return Optional containing the refund request if found.
     */
    public Optional<RefundRequest> getRefundRequestById(String requestId) {
        return refundRepository.getRefundRequestById(requestId);
    }

    /**
     * Retrieves all refund requests.
     *
     * @return List of all refund requests.
     */
    public List<RefundRequest> getAllRefundRequests() {
        return refundRepository.getAllRefundRequests();
    }

    /**
     * Retrieves refund requests by user ID.
     *
     * @param userId The user ID.
     * @return List of refund requests for the user.
     */
    public List<RefundRequest> getRefundRequestsByUserId(String userId) {
        return refundRepository.getRefundRequestsByUserId(userId);
    }

    /**
     * Retrieves refund requests submitted within a date range.
     *
     * @param startDate Start date.
     * @param endDate End date.
     * @return List of refund requests.
     */
    public List<RefundRequest> getRefundRequestsByDateRange(Date startDate, Date endDate) {
        return refundRepository.getRefundRequestsByDateRange(startDate, endDate);
    }

    /**
     * Updates an existing refund request.
     *
     * @param refundRequest The updated refund request.
     */
    public void updateRefundRequest(RefundRequest refundRequest) {
        refundRepository.updateRefundRequest(refundRequest);
    }

    /**
     * Deletes a refund request by ID.
     *
     * @param requestId The ID of the refund request.
     * @return True if deleted, false otherwise.
     */
    public boolean deleteRefundRequest(String requestId) {
        return refundRepository.deleteRefundRequest(requestId);
    }

    /**
     * Retrieves refund requests by their status.
     *
     * @param status The refund status.
     * @return List of refund requests with the given status.
     */
    public List<RefundRequest> getRefundRequestsByStatus(RefundStatus status) {
        return refundRepository.getRefundRequestsByStatus(status);
    }

    /**
     * Approves a refund request.
     *
     * @param requestId The ID of the refund request.
     */
    public void approveRefundRequest(String requestId) {
        refundRepository.updateRefundStatus(requestId, RefundStatus.APPROVED);
    }

    /**
     * Rejects a refund request.
     *
     * @param requestId The ID of the refund request.
     */
    public void rejectRefundRequest(String requestId) {
        refundRepository.updateRefundStatus(requestId, RefundStatus.REJECTED);
    }

    /**
     * Cancels a refund request.
     *
     * @param requestId The ID of the refund request.
     */
    public void cancelRefundRequest(String requestId) {
        refundRepository.updateRefundStatus(requestId, RefundStatus.CANCELLED);
    }

    /**
     * Checks if a refund request exists by ID.
     *
     * @param requestId The ID of the refund request.
     * @return True if it exists, false otherwise.
     */
    public boolean refundRequestExists(String requestId) {
        return refundRepository.getRefundRequestById(requestId).isPresent();
    }

    /**
     * Clears all refund requests (useful for testing or data reset).
     */
    public void clearAllRefundRequests() {
        refundRepository.clearAllRefundRequests();
    }
}
