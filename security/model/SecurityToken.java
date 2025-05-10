package com.myinappbilling.security.model;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents a security token used for authentication or verification purposes.
 */
public class SecurityToken {

    private String token;
    private String userId;
    private LocalDateTime issuedAt;
    private LocalDateTime expiresAt;
    private boolean isActive;
    private String ipAddress;
    private String deviceInfo;
    private String purpose; // e.g., login, transaction verification, etc.
    private int failedAttempts;

    public SecurityToken(String userId, LocalDateTime issuedAt, LocalDateTime expiresAt, String ipAddress, String deviceInfo, String purpose) {
        this.token = generateToken();
        this.userId = userId;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
        this.isActive = true;
        this.ipAddress = ipAddress;
        this.deviceInfo = deviceInfo;
        this.purpose = purpose;
        this.failedAttempts = 0;
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(LocalDateTime issuedAt) {
        this.issuedAt = issuedAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public boolean isActive() {
        return isActive && !isExpired();
    }

    public void setActive(boolean active) {
        isActive = active;
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

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public void incrementFailedAttempts() {
        this.failedAttempts++;
    }

    public void resetFailedAttempts() {
        this.failedAttempts = 0;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }

    public void deactivateIfExpired() {
        if (isExpired()) {
            isActive = false;
        }
    }

    @Override
    public String toString() {
        return "SecurityToken{" +
                "token='" + token + '\'' +
                ", userId='" + userId + '\'' +
                ", issuedAt=" + issuedAt +
                ", expiresAt=" + expiresAt +
                ", isActive=" + isActive +
                ", ipAddress='" + ipAddress + '\'' +
                ", deviceInfo='" + deviceInfo + '\'' +
                ", purpose='" + purpose + '\'' +
                ", failedAttempts=" + failedAttempts +
                '}';
    }
}
