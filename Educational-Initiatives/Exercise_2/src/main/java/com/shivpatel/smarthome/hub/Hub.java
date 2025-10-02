package com.shivpatel.smarthome.hub;

import com.shivpatel.smarthome.device.*;
import com.shivpatel.smarthome.device.exceptions.DeviceNotFoundException;
import com.shivpatel.smarthome.logger.AppLogger;
import java.time.LocalTime;
import java.util.*;

public class Hub {
    private final Map<Integer, DeviceProxy> devices = new HashMap<>();
    private final List<Observer> observers = new ArrayList<>();
    private final List<ScheduledTask> scheduledTasks = new ArrayList<>();
    private final List<Trigger> triggers = new ArrayList<>();
    private final AppLogger log = AppLogger.INSTANCE;
    private final Map<Integer, Boolean> deviceAccess = new HashMap<>();

    public void addDevice(Device d) {
        DeviceProxy proxy = new DeviceProxy(d, this);
        devices.put(d.getId(), proxy);
        deviceAccess.put(d.getId(), true);
        log.info("Hub: device added -> " + d.getType() + "(" + d.getId() + ")");
    }

    public void removeDevice(int id) {
        devices.remove(id);
        deviceAccess.remove(id);
        log.info("Hub: device removed -> id=" + id);
    }

    public Collection<DeviceProxy> listDevices() {
        return devices.values();
    }

    public boolean canAccessDevice(int id) {
        return deviceAccess.getOrDefault(id, false);
    }

    public void setDeviceAccess(int id, boolean access) {
        deviceAccess.put(id, access);
    }

    public void registerObserver(Observer o) {
        observers.add(o);
    }

    public void unregisterObserver(Observer o) {
        observers.remove(o);
    }

    public void notifyObservers(String event) {
        for (Observer o : observers) {
            try {
                o.onHubEvent(event);
            } catch (Exception e) {
                log.warn("Observer failed: " + e.getMessage());
            }
        }
    }

    public void executeCommand(String cmdLine) throws DeviceNotFoundException {
        String[] parts = cmdLine.trim().split("\s+");
        if (parts.length == 0)
            return;
        String cmd = parts[0].toLowerCase();
        switch (cmd) {
            case "turnon":
                requireArgs(parts, 2);
                int idOn = Integer.parseInt(parts[1]);
                DeviceProxy dpOn = devices.get(idOn);
                if (dpOn == null)
                    throw new DeviceNotFoundException("Device not found: " + idOn);
                dpOn.turnOn();
                notifyObservers("Device " + idOn + " turnedOn");
                break;
            case "turnoff":
                requireArgs(parts, 2);
                int idOff = Integer.parseInt(parts[1]);
                DeviceProxy dpOff = devices.get(idOff);
                if (dpOff == null)
                    throw new DeviceNotFoundException("Device not found: " + idOff);
                dpOff.turnOff();
                notifyObservers("Device " + idOff + " turnedOff");
                break;
            case "settemp":
                requireArgs(parts, 3);
                int idT = Integer.parseInt(parts[1]);
                double val = Double.parseDouble(parts[2]);
                DeviceProxy dpT = devices.get(idT);
                if (dpT == null)
                    throw new DeviceNotFoundException("Device not found: " + idT);
                Device real = dpT.getReal();
                if (real instanceof Thermostat) {
                    ((Thermostat) real).setTemperature(val);
                    AppLogger.INSTANCE.info("Set temp " + val + " on Thermostat(" + idT + ")");
                } else {
                    AppLogger.INSTANCE.warn("Device is not a thermostat: id=" + idT);
                }
                break;
            default:
                log.warn("Unknown command: " + cmdLine);
        }
    }

    private void requireArgs(String[] parts, int needed) {
        if (parts.length < needed)
            throw new IllegalArgumentException("Insufficient arguments");
    }

    // public void addSchedule(ScheduledTask t) {
    // scheduledTasks.add(t);
    // log.info("Schedule added: " + t);
    // }
    public void addSchedule(ScheduledTask task) throws DeviceNotFoundException {
        int deviceId = task.getDeviceId();
        if (!devices.containsKey(deviceId)) {
            log.warn("Attempt to schedule for non-existing device: " + deviceId);
            throw new DeviceNotFoundException("Device not found: " + deviceId);
        }

        for (ScheduledTask s : scheduledTasks) {
            if (s.getDeviceId() == deviceId && s.getTime().equals(task.getTime())) {
                log.warn("Duplicate schedule for device " + deviceId + " at " + task.getTime());
                throw new IllegalArgumentException(
                        "Schedule already exists for device " + deviceId + " at " + task.getTime());
            }
        }

        String cmd = task.getCommand().trim();
        if (cmd.isEmpty()) {
            log.warn("Attempt to add empty schedule command for device: " + deviceId);
            throw new IllegalArgumentException("Empty command");
        }

        String[] parts = cmd.split("\\s+");
        String verb = parts[0];

        try {
            switch (verb) {
                case "turnOn":
                case "turnOff":
                    if (parts.length != 2)
                        throw new IllegalArgumentException("Usage: " + verb + " <id>");
                    int targetId = Integer.parseInt(parts[1]);
                    if (!devices.containsKey(targetId))
                        throw new DeviceNotFoundException("Target device not found: " + targetId);
                    break;
                case "setTemp":
                    if (parts.length != 3)
                        throw new IllegalArgumentException("Usage: setTemp <id> <value>");
                    int tId = Integer.parseInt(parts[1]);
                    if (!devices.containsKey(tId))
                        throw new DeviceNotFoundException("Target device not found: " + tId);
                    Double.parseDouble(parts[2]); // validate numeric value
                    break;
                default:
                    throw new IllegalArgumentException("Unknown command: " + verb);
            }
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("Invalid numeric value in command: " + cmd);
        }

        scheduledTasks.add(task);
        log.info("Schedule added: {device:" + deviceId + ", time:" + task.getTime() + ", cmd:\"" + task.getCommand()
                + "\"}");
    }

    public List<ScheduledTask> getSchedules() {
        return Collections.unmodifiableList(scheduledTasks);
    }

    public void runDueSchedules(LocalTime now) {
        List<ScheduledTask> due = new ArrayList<>();
        for (ScheduledTask t : scheduledTasks) {
            if (!t.getTime().isAfter(now))
                due.add(t);
        }
        for (ScheduledTask t : due) {
            try {
                log.info("Executing scheduled: " + t);
                executeCommand(t.getCommand());
            } catch (Exception e) {
                log.error("Failed to execute scheduled: " + t, e);
            }
            scheduledTasks.remove(t);
        }
    }

    public void addTrigger(Trigger tr) throws DeviceNotFoundException {

        
        String param = tr.getParameterName();
        String op = tr.getOperator();
        double threshold = tr.getThreshold();
        String action = tr.getAction().trim();
        
        // 1) validate known parameters (only temperature supported)
        Set<String> validParams = Set.of("temperature");
        if (!validParams.contains(param)) {
            log.warn("Invalid param -> " + param);
            throw new IllegalArgumentException("Invalid param: " + param);
        }
        
        // 2) validate operator
        Set<String> validOps = Set.of(">", "<", ">=", "<=", "==");
        if (!validOps.contains(op)) {
            log.warn("Invalid operator -> " + op);
            throw new IllegalArgumentException("Invalid operator: " + op);
        }
        
        // 3) validate action syntax and target device
        if (action.isEmpty()) {
            throw new IllegalArgumentException("Missing action for trigger");
        }
        String[] parts = action.split("\\s+");
        String verb = parts[0];
        int targetId;
        try {
            switch (verb) {
                case "turnOn":
                case "turnOff":
                if (parts.length != 2)
                throw new IllegalArgumentException("Usage: " + verb + " <id>");
                targetId = Integer.parseInt(parts[1]);
                if (!devices.containsKey(targetId))
                throw new DeviceNotFoundException("Target device not found: " + targetId);
                break;
                case "setTemp":
                if (parts.length != 3)
                throw new IllegalArgumentException("Usage: setTemp <id> <value>");
                targetId = Integer.parseInt(parts[1]);
                if (!devices.containsKey(targetId))
                throw new DeviceNotFoundException("Target device not found: " + targetId);
                Double.parseDouble(parts[2]); // validate numeric threshold/value
                break;
                default:
                throw new IllegalArgumentException("Wrong action -> " + verb);
            }
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("Threshold or id not a number in action: " + action);
        }
        
        // 4) detect conflicting triggers (same param, op, threshold, same targetId,
        // opposite action)
        for (Trigger existing : triggers) {
            if (existing.getParameterName().equals(param)
            && existing.getOperator().equals(op)
                    && Double.compare(existing.getThreshold(), threshold) == 0) {

                        // parse existing action verb & target
                String[] exParts = existing.getAction().trim().split("\\s+");
                if (exParts.length >= 2) {
                    String exVerb = exParts[0];
                    int exTarget;
                    try {
                        exTarget = Integer.parseInt(exParts[1]);
                    } catch (NumberFormatException e) {
                        continue;
                    }
                    
                    // conflict if same device and opposing on/off actions
                    if (exTarget == Integer.parseInt(parts[1])) {
                        if ((exVerb.equals("turnOn") && verb.equals("turnOff")) ||
                                (exVerb.equals("turnOff") && verb.equals("turnOn"))) {
                                    log.warn("Conflicting triggers for device " + exTarget + " at " + threshold);
                            throw new IllegalArgumentException(
                                "Conflicting trigger exists for device " + exTarget + " at " + threshold);
                            }
                        }
                    }
            }
        }

        triggers.add(tr);
        log.info("Trigger added: " + tr);
    }

    public List<Trigger> getTriggers() {
        return Collections.unmodifiableList(triggers);
    }

    public void evaluateTriggers() {
        List<Thermostat> thermostats = new ArrayList<>();
        for (DeviceProxy dp : devices.values()) {
            Device d = dp;
            if (d instanceof Thermostat) {
                thermostats.add((Thermostat) d);
            }
        }
        for (Trigger tr : triggers) {
            if ("temperature".equalsIgnoreCase(tr.getParameterName())) {
                for (Thermostat t : thermostats) {
                    double val = t.getTemperature();
                    if (compare(val, tr.getOperator(), tr.getThreshold())) {
                        try {
                            log.info("Trigger fired: " + tr + " on thermostat id=" + t.getId());
                            executeCommand(tr.getAction());
                        } catch (Exception e) {
                            log.error("Trigger action failed", e);
                        }
                    }
                }
            }
        }
    }

    private boolean compare(double val, String op, double threshold) {
        switch (op) {
            case ">":
                return val > threshold;
            case "<":
                return val < threshold;
            case ">=":
                return val >= threshold;
            case "<=":
                return val <= threshold;
            case "==":
                return val == threshold;
            default:
                return false;
        }
    }
}
