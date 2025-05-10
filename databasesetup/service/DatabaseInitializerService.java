package com.myinappbilling.databasesetup.service;

import com.myinappbilling.databasesetup.model.ColumnDefinition;
import com.myinappbilling.databasesetup.model.DatabaseConfig;
import com.myinappbilling.databasesetup.model.DatabaseTable;
import com.myinappbilling.databasesetup.repository.DatabaseRepository;
import com.myinappbilling.databasesetup.validator.DatabaseConfigValidator;
import java.util.List;

/**
 * Service responsible for initializing the database schema based on configuration.
 */
public class DatabaseInitializerService {

    private final DatabaseRepository databaseRepository;
    private final DatabaseConfigValidator validator;

    public DatabaseInitializerService(DatabaseRepository databaseRepository, DatabaseConfigValidator validator) {
        this.databaseRepository = databaseRepository;
        this.validator = validator;
    }

    /**
     * Initializes the database using the given configuration.
     *
     * @param config the database configuration to apply
     */
    public void initializeDatabase(DatabaseConfig config) {
        if (!validator.isValid(config)) {
            throw new IllegalArgumentException("Invalid database configuration");
        }

        List<DatabaseTable> tables = config.getTables();
        for (DatabaseTable table : tables) {
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

            for (ColumnDefinition col : table.getColumns()) {
                if (col.getForeignKeyTable() != null && col.getForeignKeyColumn() != null) {
                    String foreignKeySql = String.format(
                        "ALTER TABLE %s ADD FOREIGN KEY (%s) REFERENCES %s(%s);",
                        table.getTableName(),
                        col.getColumnName(),
                        col.getForeignKeyTable(),
                        col.getForeignKeyColumn()
                    );
                    databaseRepository.executeSql(foreignKeySql);
                }
            }
        }
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

    /**
     * Drops the specified table from the database.
     *
     * @param tableName name of the table to drop
     */
    public void dropTable(String tableName) {
        String dropSql = "DROP TABLE IF EXISTS " + tableName + ";";
        databaseRepository.executeSql(dropSql);
    }

    /**
     * Verifies whether a table exists in the database.
     *
     * @param tableName name of the table
     * @return true if exists, false otherwise
     */
    public boolean doesTableExist(String tableName) {
        return databaseRepository.tableExists(tableName);
    }
}
