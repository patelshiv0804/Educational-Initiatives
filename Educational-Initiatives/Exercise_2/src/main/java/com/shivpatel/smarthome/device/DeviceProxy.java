package com.shivpatel.smarthome.device ;

import com.shivpatel.smarthome.hub.Hub;
import com.shivpatel.smarthome.logger.AppLogger;

public class DeviceProxy implements Device {
    private final Device real;
    private final Hub hub;

    public DeviceProxy(Device real, Hub hub) {
        this.real = real;
        this.hub = hub;
    }

    @Override
    public int getId() {
        return real.getId();
    }

    @Override
    public String getType() {
        return real.getType();
    }

    @Override
    public String getStatus() {
        return real.getStatus();
    }

    @Override
    public void turnOn() {
        if (!hub.canAccessDevice(real.getId())) {
            AppLogger.INSTANCE.warn("Access denied to " + real.getType() + "(" + real.getId() + ")");
            return;
        }
        AppLogger.INSTANCE.info("Proxy: invoking turnOn on " + real.getType() + "(" + real.getId() + ")");
        real.turnOn();
    }

    @Override
    public void turnOff() {
        if (!hub.canAccessDevice(real.getId())) {
            AppLogger.INSTANCE.warn("Access denied to " + real.getType() + "(" + real.getId() + ")");
            return;
        }
        AppLogger.INSTANCE.info("Proxy: invoking turnOff on " + real.getType() + "(" + real.getId() + ")");
        real.turnOff();
    }
}
