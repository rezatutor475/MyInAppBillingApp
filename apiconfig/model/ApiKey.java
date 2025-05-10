package com.myinappbilling.apiconfig.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents an API key used for authenticating API requests.
 */
public class ApiKey {

    private String keyId;
    private String keyValue;
    private LocalDateTime creationDate;
    private LocalDateTime expirationDate;
    private boolean active;

    public ApiKey() {
    }

    public ApiKey(String keyId, String keyValue, LocalDateTime creationDate, LocalDateTime expirationDate, boolean active) {
        this.keyId = keyId;
        this.keyValue = keyValue;
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
        this.active = active;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Checks if the API key is expired.
     * @return true if expired, false otherwise.
     */
    public boolean isExpired() {
        return expirationDate != null && expirationDate.isBefore(LocalDateTime.now());
    }

    /**
     * Deactivates the API key manually.
     */
    public void deactivate() {
        this.active = false;
    }

    /**
     * Extends the expiration date by a given number of days.
     * @param days Number of days to extend.
     */
    public void extendExpiration(int days) {
        if (this.expirationDate != null) {
            this.expirationDate = this.expirationDate.plusDays(days);
        }
    }

    @Override
    public String toString() {
        return "ApiKey{" +
                "keyId='" + keyId + '\'' +
                ", creationDate=" + creationDate +
                ", expirationDate=" + expirationDate +
                ", active=" + active +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiKey apiKey = (ApiKey) o;
        return Objects.equals(keyId, apiKey.keyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keyId);
    }
}
