package com.myinappbilling.financial.model;

/**
 * Enum representing the various statuses a refund request can have.
 */
public enum RefundStatus {

    /**
     * Refund request has been created and is waiting for review.
     */
    PENDING,

    /**
     * Refund request is currently under processing.
     */
    PROCESSING,

    /**
     * Refund request has been approved and is being paid out.
     */
    APPROVED,

    /**
     * Refund request has been denied.
     */
    REJECTED,

    /**
     * Refund has been fully processed and completed.
     */
    COMPLETED,

    /**
     * Refund request has been escalated for further review or action.
     */
    ESCALATED,

    /**
     * Refund was canceled by the user or system.
     */
    CANCELED;

    /**
     * Determines if the refund status represents a terminal state.
     *
     * @return true if the status is COMPLETED, REJECTED, or CANCELED; false otherwise.
     */
    public boolean isTerminal() {
        return this == COMPLETED || this == REJECTED || this == CANCELED;
    }

    /**
     * Determines if the refund status indicates that the request needs immediate attention.
     *
     * @return true if the status is ESCALATED or PENDING; false otherwise.
     */
    public boolean requiresAttention() {
        return this == ESCALATED || this == PENDING;
    }

    /**
     * Checks whether the refund is currently in progress.
     *
     * @return true if the status is PROCESSING or APPROVED; false otherwise.
     */
    public boolean isInProgress() {
        return this == PROCESSING || this == APPROVED;
    }
}
