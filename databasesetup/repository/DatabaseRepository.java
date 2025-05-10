package com.myinappbilling.databasesetup.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Repository responsible for executing raw SQL commands on the database.
 */
public class DatabaseRepository {

    private static final Logger LOGGER = Logger.getLogger(DatabaseRepository.class.getName());
    private String databaseUrl;

    public DatabaseRepository(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    /**
     * Executes a given SQL statement.
     *
     * @param sql the SQL command to execute
     */
    public void executeSql(String sql) {
        try (Connection connection = DriverManager.getConnection(databaseUrl);
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(sql);
            LOGGER.info("Executed SQL: " + sql);

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQL execution failed: " + sql, e);
        }
    }

    /**
     * Executes a query SQL and returns the result set.
     *
     * @param query the SELECT SQL query to execute
     * @return List of String arrays representing rows
     */
    public List<String[]> executeQuery(String query) {
        List<String[]> results = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(databaseUrl);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            int columnCount = resultSet.getMetaData().getColumnCount();

            while (resultSet.next()) {
                String[] row = new String[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    row[i] = resultSet.getString(i + 1);
                }
                results.add(row);
            }
            LOGGER.info("Executed query: " + query);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Query execution failed: " + query, e);
        }

        return results;
    }

    /**
     * Utility method to test database connectivity.
     *
     * @return true if connection is successful, false otherwise
     */
    public boolean testConnection() {
        try (Connection connection = DriverManager.getConnection(databaseUrl)) {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database connection test failed", e);
            return false;
        }
    }

    /**
     * Clears all data from a given table.
     *
     * @param tableName the name of the table to truncate
     */
    public void clearTable(String tableName) {
        String sql = "DELETE FROM " + tableName + ";";
        executeSql(sql);
        LOGGER.info("Cleared all data from table: " + tableName);
    }

    /**
     * Drops a specific column from a table.
     *
     * @param tableName the name of the table
     * @param columnName the name of the column to drop
     */
    public void dropColumn(String tableName, String columnName) {
        String sql = "ALTER TABLE " + tableName + " DROP COLUMN " + columnName + ";";
        executeSql(sql);
        LOGGER.info("Dropped column " + columnName + " from table " + tableName);
    }
}
