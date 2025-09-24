package com.shivpatel.designpattern.creation.singleton;

import java.util.Properties;
import com.shivpatel.designpattern.util.LoggerUtil;

public class ConfigurationManager {
    // volatile ensures visibility across threads
    private static volatile ConfigurationManager instance;
    private Properties config;

    // private constructor prevents external instantiation
    private ConfigurationManager() {
        config = new Properties();
        config.setProperty("app.name", "DesignPatternDemo");
        config.setProperty("app.version", "1.0.0");
        LoggerUtil.logInfo("ConfigurationManager initialized.");
    }

    // Double-checked locking for thread safety
    public static ConfigurationManager getInstance() {
        if (instance == null) {
            synchronized (ConfigurationManager.class) {
                if (instance == null) {
                    instance = new ConfigurationManager();
                }
            }
        }
        return instance;
    }

    public String getProperty(String key) {
        return config.getProperty(key);
    }

    public void setProperty(String key, String value) {
        config.setProperty(key, value);
    }
}

