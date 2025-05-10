package com.myinappbilling.apiconfig.service;

import com.myinappbilling.apiconfig.model.ApiConfiguration;
import com.myinappbilling.apiconfig.model.ApiKey;
import com.myinappbilling.apiconfig.repository.ApiConfigRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service for rotating API keys securely and managing key-related operations.
 */
public class ApiKeyRotationService {

    private final ApiConfigRepository repository;

    public ApiKeyRotationService(ApiConfigRepository repository) {
        this.repository = repository;
    }

    /**
     * Rotate API keys for a given API configuration.
     * @param configId The configuration ID.
     * @return true if rotation was successful, false otherwise.
     */
    public boolean rotateApiKeys(String configId) {
        Optional<ApiConfiguration> optionalConfig = repository.findById(configId);
        if (optionalConfig.isPresent()) {
            ApiConfiguration config = optionalConfig.get();
            List<ApiKey> apiKeys = config.getApiKeys();

            // Deactivate all current keys
            for (ApiKey key : apiKeys) {
                key.setActive(false);
                key.setDeactivatedAt(LocalDateTime.now());
            }

            // Generate and add a new active key
            ApiKey newKey = generateNewApiKey();
            apiKeys.add(newKey);

            repository.update(config);
            return true;
        }
        return false;
    }

    /**
     * Retrieve the most recently created API key for a configuration.
     * @param configId The configuration ID.
     * @return Optional containing the latest ApiKey.
     */
    public Optional<ApiKey> getLatestApiKey(String configId) {
        return repository.findById(configId)
                .flatMap(config -> config.getApiKeys().stream()
                        .max(Comparator.comparing(ApiKey::getCreatedAt)));
    }

    /**
     * Get a list of active API keys for a configuration.
     * @param configId The configuration ID.
     * @return List of active ApiKeys.
     */
    public List<ApiKey> listActiveApiKeys(String configId) {
        return repository.findById(configId)
                .map(config -> config.getApiKeys().stream()
                        .filter(ApiKey::isActive)
                        .collect(Collectors.toList()))
                .orElseGet(List::of);
    }

    /**
     * Deactivate a specific API key by key ID.
     * @param configId The configuration ID.
     * @param keyId The key ID to deactivate.
     * @return true if successful, false otherwise.
     */
    public boolean deactivateApiKey(String configId, String keyId) {
        Optional<ApiConfiguration> optionalConfig = repository.findById(configId);
        if (optionalConfig.isPresent()) {
            ApiConfiguration config = optionalConfig.get();
            for (ApiKey key : config.getApiKeys()) {
                if (key.getId().equals(keyId) && key.isActive()) {
                    key.setActive(false);
                    key.setDeactivatedAt(LocalDateTime.now());
                    repository.update(config);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Generate a new API key.
     * @return A newly generated ApiKey.
     */
    private ApiKey generateNewApiKey() {
        ApiKey newKey = new ApiKey();
        newKey.setId(UUID.randomUUID().toString());
        newKey.setKeyValue(generateSecureRandomKey());
        newKey.setCreatedAt(LocalDateTime.now());
        newKey.setActive(true);
        return newKey;
    }

    /**
     * Securely generate a random API key value.
     * @return random secure API key as String.
     */
    private String generateSecureRandomKey() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
