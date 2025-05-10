package com.myinappbilling.apiconfig.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a complete API configuration, including endpoints and keys.
 */
public class ApiConfiguration {

    private String configId;
    private String name;
    private List<ApiEndpoint> endpoints;
    private List<ApiKey> apiKeys;

    public ApiConfiguration() {
        this.endpoints = new ArrayList<>();
        this.apiKeys = new ArrayList<>();
    }

    public ApiConfiguration(String configId, String name, List<ApiEndpoint> endpoints, List<ApiKey> apiKeys) {
        this.configId = configId;
        this.name = name;
        this.endpoints = endpoints != null ? endpoints : new ArrayList<>();
        this.apiKeys = apiKeys != null ? apiKeys : new ArrayList<>();
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ApiEndpoint> getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(List<ApiEndpoint> endpoints) {
        this.endpoints = endpoints != null ? endpoints : new ArrayList<>();
    }

    public List<ApiKey> getApiKeys() {
        return apiKeys;
    }

    public void setApiKeys(List<ApiKey> apiKeys) {
        this.apiKeys = apiKeys != null ? apiKeys : new ArrayList<>();
    }

    /**
     * Checks if a specific endpoint exists in the configuration.
     * @param url The URL of the endpoint.
     * @return true if exists, false otherwise.
     */
    public boolean hasEndpoint(String url) {
        return endpoints != null && endpoints.stream()
                .anyMatch(endpoint -> endpoint.getUrl().equalsIgnoreCase(url));
    }

    /**
     * Adds a new API endpoint to the configuration.
     * @param endpoint The endpoint to add.
     */
    public void addEndpoint(ApiEndpoint endpoint) {
        if (endpoint != null) {
            endpoints.add(endpoint);
        }
    }

    /**
     * Removes an API endpoint by URL.
     * @param url The URL of the endpoint to remove.
     */
    public void removeEndpoint(String url) {
        if (url != null && endpoints != null) {
            endpoints.removeIf(endpoint -> endpoint.getUrl().equalsIgnoreCase(url));
        }
    }

    /**
     * Counts the number of active API keys.
     * @return number of active keys.
     */
    public long countActiveKeys() {
        return apiKeys != null ? apiKeys.stream().filter(ApiKey::isActive).count() : 0;
    }

    /**
     * Deactivates all expired API keys.
     */
    public void deactivateExpiredKeys() {
        if (apiKeys != null) {
            apiKeys.forEach(key -> {
                if (key.isExpired()) {
                    key.setActive(false);
                }
            });
        }
    }

    @Override
    public String toString() {
        return "ApiConfiguration{" +
                "configId='" + configId + '\'' +
                ", name='" + name + '\'' +
                ", endpoints=" + (endpoints != null ? endpoints.size() : 0) +
                ", apiKeys=" + (apiKeys != null ? apiKeys.size() : 0) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiConfiguration that = (ApiConfiguration) o;
        return Objects.equals(configId, that.configId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(configId);
    }
}
