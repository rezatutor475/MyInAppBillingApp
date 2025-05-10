package com.myinappbilling.apiconfig.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.myinappbilling.apiconfig.model.ApiConfiguration;
import com.myinappbilling.apiconfig.repository.ApiConfigRepository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * ViewModel for managing API Configuration UI-related data with enhanced features.
 */
public class ApiConfigViewModel extends ViewModel {

    private final ApiConfigRepository repository;
    private final MutableLiveData<List<ApiConfiguration>> apiConfigurations;
    private final MutableLiveData<ApiConfiguration> selectedConfig;

    public ApiConfigViewModel() {
        repository = new ApiConfigRepository();
        apiConfigurations = new MutableLiveData<>();
        selectedConfig = new MutableLiveData<>();
        loadApiConfigurations();
    }

    private void loadApiConfigurations() {
        apiConfigurations.setValue(repository.getAllConfigurations());
    }

    public LiveData<List<ApiConfiguration>> getApiConfigurations() {
        return apiConfigurations;
    }

    public LiveData<ApiConfiguration> getSelectedConfig() {
        return selectedConfig;
    }

    public void selectConfiguration(ApiConfiguration config) {
        selectedConfig.setValue(config);
    }

    public void addConfiguration(ApiConfiguration config) {
        repository.saveConfiguration(config);
        loadApiConfigurations();
    }

    public void updateConfiguration(ApiConfiguration config) {
        repository.updateConfiguration(config);
        loadApiConfigurations();
    }

    public void deleteConfiguration(ApiConfiguration config) {
        repository.deleteConfiguration(config);
        loadApiConfigurations();
    }

    public void searchConfigurations(String keyword) {
        List<ApiConfiguration> results = repository.searchConfigurations(keyword);
        apiConfigurations.setValue(results);
    }

    public void sortConfigurationsByName(boolean ascending) {
        List<ApiConfiguration> configs = apiConfigurations.getValue();
        if (configs != null) {
            configs.sort((config1, config2) -> ascending ? 
                config1.getName().compareToIgnoreCase(config2.getName()) : 
                config2.getName().compareToIgnoreCase(config1.getName()));
            apiConfigurations.setValue(configs);
        }
    }

    public void sortConfigurationsByDate(boolean ascending) {
        List<ApiConfiguration> configs = apiConfigurations.getValue();
        if (configs != null) {
            configs.sort((config1, config2) -> ascending ? 
                config1.getCreatedDate().compareTo(config2.getCreatedDate()) :
                config2.getCreatedDate().compareTo(config1.getCreatedDate()));
            apiConfigurations.setValue(configs);
        }
    }

    public void refreshConfigurations() {
        loadApiConfigurations();
    }
}
