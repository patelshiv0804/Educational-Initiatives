package com.shivpatel.smarthome.device ;

import com.shivpatel.smarthome.logger.AppLogger;

public final class DeviceFactory {
    private DeviceFactory() {
    }

    public static Device createDevice(String type, int id) {
        if (type == null)
            throw new IllegalArgumentException("Type required");
        switch (type.trim().toLowerCase()) {
            case "light":
                return new Light(id);
            case "thermostat":
                return new Thermostat(id);
            case "door":
            case "doorlock":
                return new DoorLock(id);
            default:
                AppLogger.INSTANCE.warn("Unknown device type: " + type + ". Defaulting to Light.");
                return new Light(id);
        }
    }
}
