package com.shivpatel.designpattern.creation.factory;

import com.shivpatel.designpattern.util.LoggerUtil;

import java.util.Scanner;

public class DatabaseConnectionFactoryDemo {
    public static void run() {
        LoggerUtil.logInfo("=== Factory Pattern Demo Started ===");

        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;

            while (running) {
                System.out.println("\nChoose a database type (mysql, postgresql, oracle) or type 'exit' to quit:");
                String input = scanner.nextLine().trim();

                if (input.equalsIgnoreCase("exit")) {
                    LoggerUtil.logInfo("Exiting Factory Pattern Demo...");
                    running = false;
                } else {
                    try {
                        DatabaseConnection connection = DatabaseConnectionFactory.getConnection(input);
                        LoggerUtil.logInfo("Created: " + connection.getDescription());
                        connection.connect();
                        connection.disconnect();
                    } catch (Exception e) {
                        LoggerUtil.logError("Exception: " + e.getMessage());
                    }
                }
            }
        }

        LoggerUtil.logInfo("=== Factory Pattern Demo Ended ===");
    }
}
