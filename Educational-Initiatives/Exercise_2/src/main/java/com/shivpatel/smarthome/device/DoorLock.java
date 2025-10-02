package com.shivpatel.smarthome.device ;

import com.shivpatel.smarthome.logger.AppLogger;

public class DoorLock extends AbstractDevice {
    private boolean locked = true;

    public DoorLock(int id) {
        super(id);
        locked = true;
    }

    @Override
    public String getType() {
        return "DoorLock";
    }

    @Override
    public void turnOn() {
        locked = false;
        AppLogger.INSTANCE.info(deviceInfo() + " UNLOCKED");
    }

    @Override
    public void turnOff() {
        locked = true;
        AppLogger.INSTANCE.info(deviceInfo() + " LOCKED");
    }

    @Override
    public String getStatus() {
        return locked ? "Locked" : "Unlocked";
    }
}
