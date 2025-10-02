package com.shivpatel.smarthome.device ;

public interface Device {
    int getId();

    String getType();

    String getStatus();

    void turnOn();

    void turnOff();
}
