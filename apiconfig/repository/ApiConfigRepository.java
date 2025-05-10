package com.myinappbilling.apiconfig.repository;

import com.myinappbilling.apiconfig.model.ApiConfiguration;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Repository for managing API configurations in memory.
 */
public class ApiConfigRepository {

    private final Map<String, ApiConfiguration> apiConfigStore = new ConcurrentHashMap<>();

    /**
     * Save a new API configuration.
     * @param config The API configuration to save.
     */
    public void save(ApiConfiguration config) {
        apiConfigStore.put(config.getId(), config);
    }

    /**
     * Update an existing API configuration.
     * @param config The API configuration to update.
     */
    public void update(ApiConfiguration config) {
        apiConfigStore.put(config.getId(), config);
    }

    /**
     * Find an API configuration by ID.
     * @param id The configuration ID.
     * @return Optional of ApiConfiguration.
     */
    public Optional<ApiConfiguration> findById(String id) {
        return Optional.ofNullable(apiConfigStore.get(id));
    }

    /**
     * Delete an API configuration by ID.
     * @param id The configuration ID to delete.
     */
    public void delete(String id) {
        apiConfigStore.remove(id);
    }

    /**
     * List all API configurations.
     * @return List of all ApiConfiguration.
     */
    public List<ApiConfiguration> findAll() {
        return new ArrayList<>(apiConfigStore.values());
    }

    /**
     * Search API configurations by endpoint.
     * @param endpointUrl URL to search for.
     * @return List of matching ApiConfiguration.
     */
    public List<ApiConfiguration> findByEndpointUrl(String endpointUrl) {
        List<ApiConfiguration> result = new ArrayList<>();
        for (ApiConfiguration config : apiConfigStore.values()) {
            if (config.getEndpoints().stream().anyMatch(endpoint -> endpoint.getUrl().equalsIgnoreCase(endpointUrl))) {
                result.add(config);
            }
        }
        return result;
    }

    /**
     * Search API configurations by API key ID.
     * @param apiKeyId The API key ID to search.
     * @return List of matching ApiConfiguration.
     */
    public List<ApiConfiguration> findByApiKeyId(String apiKeyId) {
        List<ApiConfiguration> result = new ArrayList<>();
        for (ApiConfiguration config : apiConfigStore.values()) {
            if (config.getApiKeys().stream().anyMatch(apiKey -> apiKey.getId().equals(apiKeyId))) {
                result.add(config);
            }
        }
        return result;
    }

    /**
     * Check if a configuration exists by ID.
     * @param id The configuration ID.
     * @return true if exists, false otherwise.
     */
    public boolean existsById(String id) {
        return apiConfigStore.containsKey(id);
    }
}
