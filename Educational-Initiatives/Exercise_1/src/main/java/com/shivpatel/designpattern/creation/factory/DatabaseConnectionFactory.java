package com.shivpatel.designpattern.creation.factory;

import com.shivpatel.designpattern.util.LoggerUtil;

public class DatabaseConnectionFactory {

    public static DatabaseConnection getConnection(String type) {
        if (type == null) {
            throw new IllegalArgumentException("Database type must not be null.");
        }

        switch (type.toLowerCase()) {
            case "mysql":
                return new MySqlConnection();
            case "postgresql":
                return new PostgreSqlConnection();
            case "oracle":
                return new OracleConnection();
            default:
                LoggerUtil.logError("Unsupported database type: " + type);
                throw new UnsupportedOperationException("Database type not supported: " + type);
        }
    }
}
