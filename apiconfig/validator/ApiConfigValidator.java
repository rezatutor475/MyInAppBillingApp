package com.myinappbilling.apiconfig.validator;

import com.myinappbilling.apiconfig.model.ApiConfiguration;
import com.myinappbilling.apiconfig.model.ApiEndpoint;
import com.myinappbilling.apiconfig.model.ApiKey;

import java.util.List;

/**
 * Validator for API Configuration objects, endpoints, and keys.
 */
public class ApiConfigValidator {

    /**
     * Validates an ApiConfiguration object.
     * @param config The ApiConfiguration to validate.
     * @throws IllegalArgumentException if validation fails.
     */
    public void validate(ApiConfiguration config) {
        if (config == null) {
            throw new IllegalArgumentException("ApiConfiguration cannot be null.");
        }

        if (config.getId() == null || config.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("ApiConfiguration ID cannot be null or empty.");
        }

        if (config.getName() == null || config.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("ApiConfiguration name cannot be null or empty.");
        }

        validateEndpoints(config.getEndpoints());
        validateApiKeys(config.getApiKeys());
    }

    /**
     * Validates a list of API endpoints.
     * @param endpoints List of ApiEndpoint.
     */
    private void validateEndpoints(List<ApiEndpoint> endpoints) {
        if (endpoints == null || endpoints.isEmpty()) {
            throw new IllegalArgumentException("ApiConfiguration must have at least one endpoint.");
        }

        for (ApiEndpoint endpoint : endpoints) {
            if (endpoint.getUrl() == null || endpoint.getUrl().trim().isEmpty()) {
                throw new IllegalArgumentException("Endpoint URL cannot be null or empty.");
            }

            if (!endpoint.getUrl().startsWith("http")) {
                throw new IllegalArgumentException("Endpoint URL must start with http or https.");
            }

            if (endpoint.getMethod() == null || endpoint.getMethod().trim().isEmpty()) {
                throw new IllegalArgumentException("Endpoint method cannot be null or empty.");
            }

            if (!isValidHttpMethod(endpoint.getMethod())) {
                throw new IllegalArgumentException("Endpoint method must be a valid HTTP method (GET, POST, PUT, DELETE, PATCH).");
            }
        }
    }

    /**
     * Validates a list of API keys.
     * @param apiKeys List of ApiKey.
     */
    private void validateApiKeys(List<ApiKey> apiKeys) {
        if (apiKeys == null || apiKeys.isEmpty()) {
            throw new IllegalArgumentException("ApiConfiguration must have at least one API key.");
        }

        for (ApiKey key : apiKeys) {
            if (key.getKey() == null || key.getKey().trim().isEmpty()) {
                throw new IllegalArgumentException("API key cannot be null or empty.");
            }

            if (key.getExpirationDate() == null) {
                throw new IllegalArgumentException("API key expiration date cannot be null.");
            }

            if (key.getExpirationDate().before(new java.util.Date())) {
                throw new IllegalArgumentException("API key expiration date must be in the future.");
            }
        }
    }

    /**
     * Checks if the given HTTP method is valid.
     * @param method HTTP method string.
     * @return true if valid, false otherwise.
     */
    private boolean isValidHttpMethod(String method) {
        return method.equalsIgnoreCase("GET") ||
               method.equalsIgnoreCase("POST") ||
               method.equalsIgnoreCase("PUT") ||
               method.equalsIgnoreCase("DELETE") ||
               method.equalsIgnoreCase("PATCH");
    }
}
