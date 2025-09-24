package com.shivpatel.designpattern.creation.factory;

import com.shivpatel.designpattern.util.LoggerUtil;

public class PostgreSqlConnection implements DatabaseConnection {
    @Override
    public void connect() {
        LoggerUtil.logInfo("Connecting to PostgreSQL Database...");
    }

    @Override
    public void disconnect() {
        LoggerUtil.logInfo("Disconnecting from PostgreSQL Database...");
    }

    @Override
    public String getDescription() {
        return "PostgreSQL Database Connection";
    }
}
