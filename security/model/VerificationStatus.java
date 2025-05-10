package com.myinappbilling.security.model;

import java.time.Instant;

/**
 * Represents the status of a security verification process.
 */
public class VerificationStatus {

    public enum Status {
        PENDING,
        VERIFIED,
        FAILED,
        EXPIRED,
        REVOKED,
        LOCKED,
        AWAITING_REVIEW
    }

    private String userId;
    private Status status;
    private String reason;
    private long timestamp;
    private int retryCount;
    private boolean notificationSent;

    public VerificationStatus(String userId, Status status, String reason, long timestamp, int retryCount, boolean notificationSent) {
        this.userId = userId;
        this.status = status;
        this.reason = reason;
        this.timestamp = timestamp;
        this.retryCount = retryCount;
        this.notificationSent = notificationSent;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public boolean isNotificationSent() {
        return notificationSent;
    }

    public void setNotificationSent(boolean notificationSent) {
        this.notificationSent = notificationSent;
    }

    public boolean isExpired() {
        long now = Instant.now().toEpochMilli();
        return status == Status.EXPIRED || (now - timestamp > 86400000); // 24 hours
    }

    public boolean canRetry() {
        return retryCount < 3 && status != Status.LOCKED;
    }

    @Override
    public String toString() {
        return "VerificationStatus{" +
                "userId='" + userId + '\'' +
                ", status=" + status +
                ", reason='" + reason + '\'' +
                ", timestamp=" + timestamp +
                ", retryCount=" + retryCount +
                ", notificationSent=" + notificationSent +
                '}';
    }
}
