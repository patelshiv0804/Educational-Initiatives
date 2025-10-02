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

    public void addSchedule(ScheduledTask t) {
        scheduledTasks.add(t);
        log.info("Schedule added: " + t);
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

    public void addTrigger(Trigger tr) {
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
