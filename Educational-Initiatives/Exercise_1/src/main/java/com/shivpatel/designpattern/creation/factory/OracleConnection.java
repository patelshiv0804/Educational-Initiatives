package com.shivpatel.designpattern .creation.factory;

import com.shivpatel.designpattern.util.LoggerUtil;

public class OracleConnection implements DatabaseConnection {
    @Override
    public void connect() {
        LoggerUtil.logInfo("Connecting to Oracle Database...");
    }

    @Override
    public void disconnect() {
        LoggerUtil.logInfo("Disconnecting from Oracle Database...");
    }

    @Override
    public String getDescription() {
        return "Oracle Database Connection";
    }
}
