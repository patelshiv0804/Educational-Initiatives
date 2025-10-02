package com.shivpatel.smarthome.device ;

import com.shivpatel.smarthome.logger.AppLogger;
// import 

public abstract class AbstractDevice implements Device {
    protected final int id;
    protected boolean isOn = false;
    protected final AppLogger log = AppLogger.INSTANCE;

    protected AbstractDevice(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void turnOn() {
        if (!isOn) {
            isOn = true;
            log.info(deviceInfo() + " turned ON");
        } else {
            log.warn(deviceInfo() + " already ON");
        }
    }

    @Override
    public void turnOff() {
        if (isOn) {
            isOn = false;
            log.info(deviceInfo() + " turned OFF");
        } else {
            log.warn(deviceInfo() + " already OFF");
        }
    }

    protected String deviceInfo() {
        return String.format("%s(id=%d)", getType(), id);
    }

    @Override
    public String getStatus() {
        return isOn ? "ON" : "OFF";
    }
}
