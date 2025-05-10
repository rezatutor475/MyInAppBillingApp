package com.myinappbilling.databasesetup.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.myinappbilling.databasesetup.model.DatabaseConfig;
import com.myinappbilling.databasesetup.model.DatabaseTable;
import com.myinappbilling.databasesetup.service.DatabaseInitializerService;
import com.myinappbilling.databasesetup.validator.DatabaseConfigValidator;
import com.myinappbilling.databasesetup.repository.DatabaseRepository;

import java.util.List;

/**
 * ViewModel for managing the database setup UI logic.
 */
public class DatabaseSetupViewModel extends ViewModel {

    private final MutableLiveData<DatabaseConfig> databaseConfigLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isConfigValidLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> setupStatusLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<DatabaseTable>> tableListLiveData = new MutableLiveData<>();

    private final DatabaseInitializerService initializerService;
    private final DatabaseConfigValidator configValidator;
    private final DatabaseRepository databaseRepository;

    public DatabaseSetupViewModel(DatabaseInitializerService initializerService,
                                   DatabaseConfigValidator configValidator,
                                   DatabaseRepository databaseRepository) {
        this.initializerService = initializerService;
        this.configValidator = configValidator;
        this.databaseRepository = databaseRepository;
    }

    public LiveData<DatabaseConfig> getDatabaseConfig() {
        return databaseConfigLiveData;
    }

    public LiveData<Boolean> getIsConfigValid() {
        return isConfigValidLiveData;
    }

    public LiveData<String> getSetupStatus() {
        return setupStatusLiveData;
    }

    public LiveData<List<DatabaseTable>> getTableList() {
        return tableListLiveData;
    }

    public void updateDatabaseConfig(DatabaseConfig config) {
        databaseConfigLiveData.setValue(config);
        boolean isValid = configValidator.validate(config);
        isConfigValidLiveData.setValue(isValid);
    }

    public void initializeDatabase() {
        DatabaseConfig config = databaseConfigLiveData.getValue();
        if (config != null && Boolean.TRUE.equals(isConfigValidLiveData.getValue())) {
            boolean success = initializerService.initialize(config);
            if (success) {
                setupStatusLiveData.setValue("Database setup successful.");
                loadDatabaseTables();
            } else {
                setupStatusLiveData.setValue("Database setup failed.");
            }
        } else {
            setupStatusLiveData.setValue("Invalid configuration. Please check your input.");
        }
    }

    public void loadDatabaseTables() {
        DatabaseConfig config = databaseConfigLiveData.getValue();
        if (config != null) {
            List<DatabaseTable> tables = databaseRepository.fetchTables(config);
            tableListLiveData.setValue(tables);
        }
    }
} 
