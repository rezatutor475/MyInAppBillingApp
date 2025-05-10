package com.myinappbilling.databasesetup.model;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents a database table structure.
 */
public class DatabaseTable {

    private String tableName;
    private List<ColumnDefinition> columns;
    private String primaryKey;
    private boolean autoIncrementPrimaryKey;
    private List<String> indexes;

    public DatabaseTable() {
    }

    public DatabaseTable(String tableName, List<ColumnDefinition> columns, String primaryKey,
                          boolean autoIncrementPrimaryKey, List<String> indexes) {
        this.tableName = tableName;
        this.columns = columns;
        this.primaryKey = primaryKey;
        this.autoIncrementPrimaryKey = autoIncrementPrimaryKey;
        this.indexes = indexes;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<ColumnDefinition> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnDefinition> columns) {
        this.columns = columns;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public boolean isAutoIncrementPrimaryKey() {
        return autoIncrementPrimaryKey;
    }

    public void setAutoIncrementPrimaryKey(boolean autoIncrementPrimaryKey) {
        this.autoIncrementPrimaryKey = autoIncrementPrimaryKey;
    }

    public List<String> getIndexes() {
        return indexes;
    }

    public void setIndexes(List<String> indexes) {
        this.indexes = indexes;
    }

    public Optional<ColumnDefinition> getColumnByName(String columnName) {
        return columns.stream().filter(c -> c.getColumnName().equalsIgnoreCase(columnName)).findFirst();
    }

    public boolean hasColumn(String columnName) {
        return columns.stream().anyMatch(c -> c.getColumnName().equalsIgnoreCase(columnName));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DatabaseTable that = (DatabaseTable) o;
        return autoIncrementPrimaryKey == that.autoIncrementPrimaryKey &&
                Objects.equals(tableName, that.tableName) &&
                Objects.equals(columns, that.columns) &&
                Objects.equals(primaryKey, that.primaryKey) &&
                Objects.equals(indexes, that.indexes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableName, columns, primaryKey, autoIncrementPrimaryKey, indexes);
    }

    @Override
    public String toString() {
        return "DatabaseTable{" +
                "tableName='" + tableName + '\'' +
                ", columns=" + columns +
                ", primaryKey='" + primaryKey + '\'' +
                ", autoIncrementPrimaryKey=" + autoIncrementPrimaryKey +
                ", indexes=" + indexes +
                '}';
    }
}
