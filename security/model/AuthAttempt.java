package com.myinappbilling.security.model;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents an authentication attempt by a user.
 */
public class AuthAttempt {

    private String attemptId;
    private String userId;
    private LocalDateTime attemptTime;
    private boolean successful;
    private String ipAddress;
    private String deviceInfo;
    private String failureReason;
    private String location;

    public AuthAttempt(String userId, LocalDateTime attemptTime, boolean successful, String ipAddress, String deviceInfo, String failureReason, String location) {
        this.attemptId = generateAttemptId();
        this.userId = userId;
        this.attemptTime = attemptTime;
        this.successful = successful;
        this.ipAddress = ipAddress;
        this.deviceInfo = deviceInfo;
        this.failureReason = failureReason;
        this.location = location;
    }

    private String generateAttemptId() {
        return UUID.randomUUID().toString();
    }

    public String getAttemptId() {
        return attemptId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getAttemptTime() {
        return attemptTime;
    }

    public void setAttemptTime(LocalDateTime attemptTime) {
        this.attemptTime = attemptTime;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isSuspiciousAttempt() {
        return !successful && (failureReason != null && !failureReason.isEmpty());
    }

    @Override
    public String toString() {
        return "AuthAttempt{" +
                "attemptId='" + attemptId + '\'' +
                ", userId='" + userId + '\'' +
                ", attemptTime=" + attemptTime +
                ", successful=" + successful +
                ", ipAddress='" + ipAddress + '\'' +
                ", deviceInfo='" + deviceInfo + '\'' +
                ", failureReason='" + failureReason + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
