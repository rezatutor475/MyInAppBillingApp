package com.myinappbilling.email.model;

import java.util.Objects;

/**
 * Represents the status of an email message with additional metadata.
 */
public class EmailStatus {

    public enum Status {
        PENDING,
        SENT,
        FAILED,
        QUEUED,
        DELIVERED,
        BOUNCED,
        READ,
        ARCHIVED
    }

    private Status status;
    private String message;
    private long timestamp;
    private int retryCount;
    private boolean requiresAttention;

    public EmailStatus() {
        this.status = Status.PENDING;
        this.timestamp = System.currentTimeMillis();
        this.retryCount = 0;
        this.requiresAttention = false;
    }

    public EmailStatus(Status status, String message, long timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
        this.retryCount = 0;
        this.requiresAttention = false;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public void incrementRetryCount() {
        this.retryCount++;
    }

    public boolean isRequiresAttention() {
        return requiresAttention;
    }

    public void setRequiresAttention(boolean requiresAttention) {
        this.requiresAttention = requiresAttention;
    }

    public boolean isFinalStatus() {
        return status == Status.SENT || status == Status.FAILED || status == Status.DELIVERED || status == Status.BOUNCED || status == Status.ARCHIVED;
    }

    public boolean isSuccessful() {
        return status == Status.SENT || status == Status.DELIVERED || status == Status.READ;
    }

    @Override
    public String toString() {
        return "EmailStatus{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", timestamp=" + timestamp +
                ", retryCount=" + retryCount +
                ", requiresAttention=" + requiresAttention +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailStatus that = (EmailStatus) o;
        return timestamp == that.timestamp &&
               retryCount == that.retryCount &&
               requiresAttention == that.requiresAttention &&
               status == that.status &&
               Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, message, timestamp, retryCount, requiresAttention);
    }
}
