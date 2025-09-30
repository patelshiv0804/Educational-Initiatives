package com.shivpatel.smarthome.hub ;

import java.time.LocalTime;

public class ScheduledTask {
    private final int deviceId;
    private final LocalTime time;
    private final String command;

    public ScheduledTask(int deviceId, LocalTime time, String command) {
        this.deviceId = deviceId;
        this.time = time;
        this.command = command;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getCommand() {
        return command;
    }

    @Override
    public String toString() {
        return String.format("{device:%d, time:%s, cmd:\"%s\"}", deviceId, time, command);
    }
}
