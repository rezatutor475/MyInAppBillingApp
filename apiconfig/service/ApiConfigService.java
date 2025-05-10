package com.myinappbilling.apiconfig.service;

import com.myinappbilling.apiconfig.model.ApiConfiguration;
import com.myinappbilling.apiconfig.model.ApiEndpoint;
import com.myinappbilling.apiconfig.model.ApiKey;
import com.myinappbilling.apiconfig.repository.ApiConfigRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing API configurations with advanced operations.
 */
public class ApiConfigService {

    private final ApiConfigRepository repository;

    public ApiConfigService(ApiConfigRepository repository) {
        this.repository = repository;
    }

    public void saveConfiguration(ApiConfiguration config) {
        repository.save(config);
    }

    public Optional<ApiConfiguration> getConfigurationById(String configId) {
        return repository.findById(configId);
    }

    public void updateConfiguration(ApiConfiguration config) {
        repository.update(config);
    }

    public void deleteConfiguration(String configId) {
        repository.delete(configId);
    }

    public List<ApiConfiguration> listAllConfigurations() {
        return repository.findAll();
    }

    public boolean addEndpointToConfig(String configId, ApiEndpoint endpoint) {
        Optional<ApiConfiguration> optionalConfig = repository.findById(configId);
        if (optionalConfig.isPresent()) {
            ApiConfiguration config = optionalConfig.get();
            if (config.getEndpoints() == null) {
                config.setEndpoints(new ArrayList<>());
            }
            config.getEndpoints().add(endpoint);
            repository.update(config);
            return true;
        }
        return false;
    }

    public boolean addApiKeyToConfig(String configId, ApiKey apiKey) {
        Optional<ApiConfiguration> optionalConfig = repository.findById(configId);
        if (optionalConfig.isPresent()) {
            ApiConfiguration config = optionalConfig.get();
            if (config.getApiKeys() == null) {
                config.setApiKeys(new ArrayList<>());
            }
            config.getApiKeys().add(apiKey);
            repository.update(config);
            return true;
        }
        return false;
    }

    /**
     * Search configurations by name containing a keyword.
     * @param keyword keyword to search for.
     * @return list of matching configurations.
     */
    public List<ApiConfiguration> searchConfigurationsByName(String keyword) {
        return repository.findAll().stream()
                .filter(config -> config.getName() != null && config.getName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * List configurations with a minimum number of active API keys.
     * @param minActiveKeys minimum active keys required.
     * @return list of matching configurations.
     */
    public List<ApiConfiguration> listConfigsByActiveKeyThreshold(long minActiveKeys) {
        return repository.findAll().stream()
                .filter(config -> config.countActiveKeys() >= minActiveKeys)
                .collect(Collectors.toList());
    }

    /**
     * Deactivate all API keys of a specific configuration.
     * @param configId ID of the configuration.
     * @return true if successful, false otherwise.
     */
    public boolean deactivateAllApiKeys(String configId) {
        Optional<ApiConfiguration> optionalConfig = repository.findById(configId);
        if (optionalConfig.isPresent()) {
            ApiConfiguration config = optionalConfig.get();
            if (config.getApiKeys() != null) {
                config.getApiKeys().forEach(apiKey -> apiKey.setActive(false));
                repository.update(config);
                return true;
            }
        }
        return false;
    }
}
