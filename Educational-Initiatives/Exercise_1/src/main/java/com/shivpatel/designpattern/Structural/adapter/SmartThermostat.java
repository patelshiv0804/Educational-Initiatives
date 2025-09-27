package com.shivpatel.designpattern.Structural.adapter;

public class SmartThermostat implements SmartDevice {
    @Override
    public void turnOn() {
        System.out.println("Thermostat heating ON 🌡️");
    }

    @Override
    public void turnOff() {
        System.out.println("Thermostat heating OFF 🌡️");
    }

    @Override
    public String getName() {
        return "Smart Thermostat";
    }
}
