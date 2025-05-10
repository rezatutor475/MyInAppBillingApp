package com.myinappbilling.databasesetup.validator;

import com.myinappbilling.databasesetup.model.DatabaseConfig;

import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * Validates the configuration settings for the database.
 */
public class DatabaseConfigValidator {

    private static final Logger LOGGER = Logger.getLogger(DatabaseConfigValidator.class.getName());
    private static final Pattern JDBC_URL_PATTERN = Pattern.compile("^jdbc:\\w+:.*$");

    /**
     * Validates the given DatabaseConfig object.
     *
     * @param config the database configuration to validate
     * @return true if configuration is valid, false otherwise
     */
    public boolean validate(DatabaseConfig config) {
        if (config == null) {
            LOGGER.severe("DatabaseConfig is null");
            return false;
        }

        if (!validateUrl(config.getUrl())) {
            return false;
        }

        if (!validateCredential("username", config.getUsername())) {
            return false;
        }

        if (!validateCredential("password", config.getPassword())) {
            return false;
        }

        LOGGER.info("DatabaseConfig validation passed");
        return true;
    }

    private boolean validateUrl(String url) {
        if (isEmpty(url)) {
            LOGGER.warning("Database URL is missing");
            return false;
        }

        if (!JDBC_URL_PATTERN.matcher(url).matches()) {
            LOGGER.warning("Database URL format is invalid");
            return false;
        }

        return true;
    }

    private boolean validateCredential(String field, String value) {
        if (isEmpty(value)) {
            LOGGER.warning("Database " + field + " is missing");
            return false;
        }
        return true;
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}
