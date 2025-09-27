package com.shivpatel.designpattern.Creational.factory;

import com.shivpatel.designpattern.util.LoggerUtil;

public class MySqlConnection implements DatabaseConnection {
    @Override
    public void connect() {
        LoggerUtil.logInfo("Connecting to MySQL Database...");
    }

    @Override
    public void disconnect() {
        LoggerUtil.logInfo("Disconnecting from MySQL Database...");
    }

    @Override
    public String getDescription() {
        return "MySQL Database Connection";
    }
}
