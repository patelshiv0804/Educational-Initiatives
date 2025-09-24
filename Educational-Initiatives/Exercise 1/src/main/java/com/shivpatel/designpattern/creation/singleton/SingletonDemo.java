package com.shivpatel.designpattern.creation.singleton;

import com.shivpatel.designpattern.util.LoggerUtil;

public class SingletonDemo {
    public static void run() {
        LoggerUtil.logInfo("=== Singleton Pattern Demo Started ===");

        ConfigurationManager config1 = ConfigurationManager.getInstance();
        ConfigurationManager config2 = ConfigurationManager.getInstance();

        LoggerUtil.logInfo("App Name: " + config1.getProperty("app.name"));
        LoggerUtil.logInfo("App Version: " + config1.getProperty("app.version"));

        // Change property through one instance
        config1.setProperty("app.name", "UpdatedDemo");

        // Check if the other instance reflects the change
        LoggerUtil.logInfo("Config2 App Name after update: " + config2.getProperty("app.name"));

        // Verify same instance
        if (config1 == config2) {
            LoggerUtil.logInfo("Both references point to the SAME instance!");
        } else {
            LoggerUtil.logError("Different instances detected (should not happen).");
        }

        LoggerUtil.logInfo("=== Singleton Pattern Demo Ended ===");
    }
}
