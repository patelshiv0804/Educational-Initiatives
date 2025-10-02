package com.shivpatel.smarthome.device ;

import com.shivpatel.smarthome.logger.AppLogger;

public class Thermostat extends AbstractDevice {
    private double temperature = 22.0;

    public Thermostat(int id) {
        super(id);
    }

    @Override
    public String getType() {
        return "Thermostat";
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
        if (!isOn) {
            isOn = true;
        }
        AppLogger.INSTANCE.info(deviceInfo() + " temperature set to " + temperature);
    }

    @Override
    public String getStatus() {
        return super.getStatus() + String.format(" (temp=%.1f)", temperature);
    }
}
