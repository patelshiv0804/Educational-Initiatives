// package exercise2.smarthome.logger;
package com.shivpatel.smarthome.logger ;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public enum AppLogger {
    INSTANCE;
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private String now() { return LocalDateTime.now().format(fmt); }
    public void info(String msg) { System.out.println(now() + " [INFO]  " + safe(msg)); }
    public void warn(String msg) { System.out.println(now() + " [WARN]  " + safe(msg)); }
    public void error(String msg, Throwable t) {
        System.err.println(now() + " [ERROR] " + safe(msg));
        if (t != null) t.printStackTrace(System.err);
    }
    private String safe(String s) { return s == null ? "<null>" : s; }
}
