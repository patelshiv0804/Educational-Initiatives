package com.shivpatel.smarthome;
// package com.shivpatel.designpattern.Behavioral.observer;

import com.shivpatel.smarthome.device.*;
import com.shivpatel.smarthome.device.exceptions.DeviceNotFoundException;
import com.shivpatel.smarthome.hub.*;
import com.shivpatel.smarthome.logger.AppLogger;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;

public class SmartHomeMain {
    private static final AppLogger log = AppLogger.INSTANCE;
    private static final Hub hub = new Hub();
    private static final Scanner sc = new Scanner(System.in);
    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("HH:mm");

    public static void main(String[] args) {
        log.info("Smart Home System starting...");
        seedSampleDevices();
        hub.registerObserver(event -> System.out.println("[Observer] Hub event: " + event));
        printHelp();
        String line;
        while (true) {
            System.out.print("\n> ");
            line = sc.nextLine();
            if (line == null)
                continue;
            String cmd = line.trim();
            if (cmd.isEmpty())
                continue;
            if ("exit".equalsIgnoreCase(cmd)) {
                log.info("Exiting Smart Home System.");
                break;
            }
            try {
                handleCommand(cmd);
            } catch (Exception e) {
                log.error("Command failed: " + cmd, e);
            }
        }
        sc.close();
    }

    private static void seedSampleDevices() {
        Device d1 = DeviceFactory.createDevice("light", 1);
        Device d2 = DeviceFactory.createDevice("thermostat", 2);
        Device d3 = DeviceFactory.createDevice("door", 3);
        hub.addDevice(d1);
        hub.addDevice(d2);
        hub.addDevice(d3);
    }

    private static void handleCommand(String cmdLine) throws DeviceNotFoundException {
        String[] parts = cmdLine.split("\s+");
        String cmd = parts[0].toLowerCase();
        switch (cmd) {
            case "help":
                printHelp();
                break;
            case "list":
                listDevices();
                break;
            case "turnon":
            case "turnoff":
                hub.executeCommand(cmdLine);
                break;
            case "settemp":
                hub.executeCommand(cmdLine);
                break;
            case "setschedule":

                try {
                    int devId = Integer.parseInt(parts[1]);
                    LocalTime t = LocalTime.parse(parts[2]);
                    String command = String.join(" ", Arrays.copyOfRange(parts, 3, parts.length));
                    hub.addSchedule(new ScheduledTask(devId, t, command));
                    System.out.println("Schedule added: {device:" + devId + ", time:" + t + ", cmd:\"" + cmd + "\"}");
                } catch (DeviceNotFoundException e) {
                    System.out.println("[ERROR] " + e.getMessage());
                } catch (IllegalArgumentException e) {
                    System.out.println("[ERROR] " + e.getMessage());
                } catch (Exception e) {
                    System.out
                            .println("[ERROR] Invalid command format. Usage: setSchedule <deviceId> <HH:mm> <command>");
                }
                break;
            case "run-schedules":
                if (hub.getSchedules() == null || hub.getSchedules().isEmpty()) {
                    System.out.println("No scheduled tasks available.");
                } else {
                    hub.runDueSchedules(LocalTime.now()); // or the existing method name that executes schedules
                }
                break;

            case "addtrigger":
                if (parts.length < 5) {
                    log.warn("Usage: addTrigger <param> <op> <threshold> <action>");
                    return;
                }
                try {
                    // existing parsing code produces param, op, threshold, action
                    String param = parts[1];
                    String op = parts[2];
                    double threshold = Double.parseDouble(parts[3]);
                    String action = String.join(" ", Arrays.copyOfRange(parts, 4, parts.length));
                    hub.addTrigger(new Trigger(param, op, threshold, action));
                    System.out.println("Trigger added: {param:" + param + " " + op + " " + threshold + " -> action:\""
                            + action + "\"}");
                } catch (NumberFormatException e) {
                    System.out.println("[ERROR] Threshold not a number.");
                } catch (DeviceNotFoundException e) {
                    System.out.println("[ERROR] " + e.getMessage());
                } catch (IllegalArgumentException e) {
                    System.out.println("[ERROR] " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("[ERROR] Invalid command. Usage: addTrigger <param> <op> <threshold> <action>");
                }
                break;
            case "evaluate-triggers":
                hub.evaluateTriggers();
                break;
            default:
                log.warn("Unknown command: " + cmdLine);
        }
    }

    private static String join(String[] parts, int from) {
        StringBuilder sb = new StringBuilder();
        for (int i = from; i < parts.length; i++) {
            if (i > from)
                sb.append(" ");
            sb.append(parts[i]);
        }
        return sb.toString();
    }

    private static void listDevices() {
        System.out.println("Devices:");
        for (DeviceProxy dp : hub.listDevices()) {
            System.out.printf("  id=%d type=%s status=%s%n", dp.getId(), dp.getType(), dp.getStatus());
        }
        System.out.println("Schedules:");
        for (ScheduledTask s : hub.getSchedules())
            System.out.println("  " + s);
        System.out.println("Triggers:");
        for (Trigger t : hub.getTriggers())
            System.out.println("  " + t);
    }

    private static void printHelp() {
        System.out.println("=== Smart Home System Commands ===");
        System.out.println("list");
        System.out.println("turnOn <id>");
        System.out.println("turnOff <id>");
        System.out.println("setTemp <id> <value>");
        System.out.println("setSchedule <deviceId> <HH:mm> <command (eg: turnOn 1)>");
        System.out.println("run-schedules");
        System.out.println("addTrigger <param> <op> <threshold> <action (eg: turnOff 1)>");
        System.out.println("evaluate-triggers");
        System.out.println("exit");
    }
}
