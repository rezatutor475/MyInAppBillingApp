package com.myinappbilling.financial.model;

import java.util.Date;
import java.util.UUID;
import java.util.Objects;

/**
 * Represents a request for a refund initiated by a user.
 */
public class RefundRequest {

    private String refundRequestId;
    private String transactionId;
    private String userId;
    private Date requestDate;
    private String reason;
    private RefundStatus status;
    private String reviewerComments;
    private Date reviewedDate;
    private boolean isEscalated;
    private Date resolvedDate;
    private String escalationReason;

    public RefundRequest() {
        this.refundRequestId = UUID.randomUUID().toString();
        this.requestDate = new Date();
        this.status = RefundStatus.PENDING;
        this.isEscalated = false;
    }

    public RefundRequest(String transactionId, String userId, String reason) {
        this();
        this.transactionId = transactionId;
        this.userId = userId;
        this.reason = reason;
    }

    // Getters and setters

    public String getRefundRequestId() {
        return refundRequestId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public RefundStatus getStatus() {
        return status;
    }

    public void setStatus(RefundStatus status) {
        this.status = status;
    }

    public String getReviewerComments() {
        return reviewerComments;
    }

    public void setReviewerComments(String reviewerComments) {
        this.reviewerComments = reviewerComments;
    }

    public Date getReviewedDate() {
        return reviewedDate;
    }

    public void setReviewedDate(Date reviewedDate) {
        this.reviewedDate = reviewedDate;
    }

    public boolean isEscalated() {
        return isEscalated;
    }

    public void setEscalated(boolean escalated) {
        isEscalated = escalated;
    }

    public Date getResolvedDate() {
        return resolvedDate;
    }

    public void setResolvedDate(Date resolvedDate) {
        this.resolvedDate = resolvedDate;
    }

    public String getEscalationReason() {
        return escalationReason;
    }

    public void setEscalationReason(String escalationReason) {
        this.escalationReason = escalationReason;
    }

    public boolean isResolved() {
        return this.status == RefundStatus.APPROVED || this.status == RefundStatus.REJECTED || this.status == RefundStatus.COMPLETED;
    }

    public boolean needsReview() {
        return this.status == RefundStatus.PENDING || this.status == RefundStatus.ESCALATED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RefundRequest that = (RefundRequest) o;
        return Objects.equals(refundRequestId, that.refundRequestId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(refundRequestId);
    }
}

/**
 * Enum representing the status of a refund request.
 */
enum RefundStatus {
    PENDING,
    APPROVED,
    REJECTED,
    PROCESSING,
    COMPLETED,
    ESCALATED
}
