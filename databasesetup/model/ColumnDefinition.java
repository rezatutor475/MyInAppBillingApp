package com.myinappbilling.databasesetup.model;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Represents the definition of a single column in a database table.
 */
public class ColumnDefinition {

    private String columnName;
    private String dataType;
    private boolean isNullable;
    private boolean isAutoIncrement;
    private boolean isUnique;
    private String defaultValue;
    private String foreignKeyTable;
    private String foreignKeyColumn;
    private String checkConstraint;
    private String comment;

    public ColumnDefinition() {
    }

    public ColumnDefinition(String columnName, String dataType, boolean isNullable,
                             boolean isAutoIncrement, boolean isUnique, String defaultValue,
                             String foreignKeyTable, String foreignKeyColumn,
                             String checkConstraint, String comment) {
        this.columnName = columnName;
        this.dataType = dataType;
        this.isNullable = isNullable;
        this.isAutoIncrement = isAutoIncrement;
        this.isUnique = isUnique;
        this.defaultValue = defaultValue;
        this.foreignKeyTable = foreignKeyTable;
        this.foreignKeyColumn = foreignKeyColumn;
        this.checkConstraint = checkConstraint;
        this.comment = comment;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public boolean isNullable() {
        return isNullable;
    }

    public void setNullable(boolean nullable) {
        isNullable = nullable;
    }

    public boolean isAutoIncrement() {
        return isAutoIncrement;
    }

    public void setAutoIncrement(boolean autoIncrement) {
        isAutoIncrement = autoIncrement;
    }

    public boolean isUnique() {
        return isUnique;
    }

    public void setUnique(boolean unique) {
        isUnique = unique;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getForeignKeyTable() {
        return foreignKeyTable;
    }

    public void setForeignKeyTable(String foreignKeyTable) {
        this.foreignKeyTable = foreignKeyTable;
    }

    public String getForeignKeyColumn() {
        return foreignKeyColumn;
    }

    public void setForeignKeyColumn(String foreignKeyColumn) {
        this.foreignKeyColumn = foreignKeyColumn;
    }

    public String getCheckConstraint() {
        return checkConstraint;
    }

    public void setCheckConstraint(String checkConstraint) {
        this.checkConstraint = checkConstraint;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isValidColumnName() {
        return columnName != null && Pattern.matches("^[a-zA-Z_][a-zA-Z0-9_]*$", columnName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColumnDefinition that = (ColumnDefinition) o;
        return isNullable == that.isNullable &&
                isAutoIncrement == that.isAutoIncrement &&
                isUnique == that.isUnique &&
                Objects.equals(columnName, that.columnName) &&
                Objects.equals(dataType, that.dataType) &&
                Objects.equals(defaultValue, that.defaultValue) &&
                Objects.equals(foreignKeyTable, that.foreignKeyTable) &&
                Objects.equals(foreignKeyColumn, that.foreignKeyColumn) &&
                Objects.equals(checkConstraint, that.checkConstraint) &&
                Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(columnName, dataType, isNullable, isAutoIncrement,
                isUnique, defaultValue, foreignKeyTable, foreignKeyColumn,
                checkConstraint, comment);
    }

    @Override
    public String toString() {
        return "ColumnDefinition{" +
                "columnName='" + columnName + '\'' +
                ", dataType='" + dataType + '\'' +
                ", isNullable=" + isNullable +
                ", isAutoIncrement=" + isAutoIncrement +
                ", isUnique=" + isUnique +
                ", defaultValue='" + defaultValue + '\'' +
                ", foreignKeyTable='" + foreignKeyTable + '\'' +
                ", foreignKeyColumn='" + foreignKeyColumn + '\'' +
                ", checkConstraint='" + checkConstraint + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
