package com.myinappbilling.databasesetup.model;

import java.util.Objects;

/**
 * Represents the configuration for a database connection with extended capabilities.
 */
public class DatabaseConfig {

    private String databaseName;
    private String host;
    private int port;
    private String username;
    private String password;
    private String driver;
    private boolean useSSL;
    private int connectionTimeout;

    public DatabaseConfig() {
    }

    public DatabaseConfig(String databaseName, String host, int port, String username, String password, String driver, boolean useSSL, int connectionTimeout) {
        this.databaseName = databaseName;
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.driver = driver;
        this.useSSL = useSSL;
        this.connectionTimeout = connectionTimeout;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public boolean isUseSSL() {
        return useSSL;
    }

    public void setUseSSL(boolean useSSL) {
        this.useSSL = useSSL;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    /**
     * Validates if the minimum required fields are set for connection.
     * @return true if valid, false otherwise.
     */
    public boolean isValidConfig() {
        return databaseName != null && !databaseName.isEmpty() &&
               host != null && !host.isEmpty() &&
               port > 0 &&
               username != null && !username.isEmpty() &&
               password != null && !password.isEmpty() &&
               driver != null && !driver.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DatabaseConfig that = (DatabaseConfig) o;
        return port == that.port &&
                useSSL == that.useSSL &&
                connectionTimeout == that.connectionTimeout &&
                Objects.equals(databaseName, that.databaseName) &&
                Objects.equals(host, that.host) &&
                Objects.equals(username, that.username) &&
                Objects.equals(password, that.password) &&
                Objects.equals(driver, that.driver);
    }

    @Override
    public int hashCode() {
        return Objects.hash(databaseName, host, port, username, password, driver, useSSL, connectionTimeout);
    }

    @Override
    public String toString() {
        return "DatabaseConfig{" +
                "databaseName='" + databaseName + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", username='" + username + '\'' +
                ", driver='" + driver + '\'' +
                ", useSSL=" + useSSL +
                ", connectionTimeout=" + connectionTimeout +
                '}';
    }
}
