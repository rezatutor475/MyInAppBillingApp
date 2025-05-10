package com.myinappbilling.databasesetup.service;

import com.myinappbilling.databasesetup.model.ColumnDefinition;
import com.myinappbilling.databasesetup.model.DatabaseTable;
import com.myinappbilling.databasesetup.repository.DatabaseRepository;
import java.util.List;
import java.util.logging.Logger;

/**
 * Service responsible for handling database schema migrations.
 */
public class MigrationService {

    private final DatabaseRepository databaseRepository;
    private static final Logger LOGGER = Logger.getLogger(MigrationService.class.getName());

    public MigrationService(DatabaseRepository databaseRepository) {
        this.databaseRepository = databaseRepository;
    }

    /**
     * Applies a list of migration SQL statements.
     *
     * @param migrationSqlList list of SQL statements to apply
     */
    public void applyMigrations(List<String> migrationSqlList) {
        for (String sql : migrationSqlList) {
            try {
                LOGGER.info("Applying migration: " + sql);
                databaseRepository.executeSql(sql);
            } catch (Exception e) {
                LOGGER.severe("Failed to apply migration: " + sql + ". Error: " + e.getMessage());
            }
        }
    }

    /**
     * Drops a table if it exists in the database.
     *
     * @param tableName the name of the table to drop
     */
    public void dropTable(String tableName) {
        String dropSql = "DROP TABLE IF EXISTS " + tableName + ";";
        databaseRepository.executeSql(dropSql);
        LOGGER.info("Dropped table if existed: " + tableName);
    }

    /**
     * Renames an existing table.
     *
     * @param oldName the current name of the table
     * @param newName the new name for the table
     */
    public void renameTable(String oldName, String newName) {
        String renameSql = "ALTER TABLE " + oldName + " RENAME TO " + newName + ";";
        databaseRepository.executeSql(renameSql);
        LOGGER.info("Renamed table from " + oldName + " to " + newName);
    }

    /**
     * Adds a new table to the database.
     *
     * @param table the table structure to be added
     */
    public void addTable(DatabaseTable table) {
        StringBuilder createTableSql = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
                .append(table.getTableName())
                .append(" (");

        List<ColumnDefinition> columns = table.getColumns();
        for (int i = 0; i < columns.size(); i++) {
            ColumnDefinition col = columns.get(i);
            createTableSql.append(buildColumnDefinition(col));
            if (i < columns.size() - 1) {
                createTableSql.append(", ");
            }
        }
        createTableSql.append(");");

        databaseRepository.executeSql(createTableSql.toString());
        LOGGER.info("Added new table: " + table.getTableName());
    }

    /**
     * Alters a table by adding a new column.
     *
     * @param tableName the table to be altered
     * @param column the new column to be added
     */
    public void addColumnToTable(String tableName, ColumnDefinition column) {
        String alterSql = "ALTER TABLE " + tableName + " ADD COLUMN " + buildColumnDefinition(column) + ";";
        databaseRepository.executeSql(alterSql);
        LOGGER.info("Added column " + column.getColumnName() + " to table " + tableName);
    }

    private String buildColumnDefinition(ColumnDefinition column) {
        StringBuilder sb = new StringBuilder();
        sb.append(column.getColumnName()).append(" ").append(column.getDataType());

        if (!column.isNullable()) {
            sb.append(" NOT NULL");
        }
        if (column.isAutoIncrement()) {
            sb.append(" AUTO_INCREMENT");
        }
        if (column.isUnique()) {
            sb.append(" UNIQUE");
        }
        if (column.getDefaultValue() != null) {
            sb.append(" DEFAULT '").append(column.getDefaultValue()).append("'");
        }
        if (column.getCheckConstraint() != null) {
            sb.append(" CHECK (").append(column.getCheckConstraint()).append(")");
        }
        return sb.toString();
    }
}
