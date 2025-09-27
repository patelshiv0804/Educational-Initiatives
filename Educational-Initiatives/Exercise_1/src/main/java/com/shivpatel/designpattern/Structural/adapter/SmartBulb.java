package com.shivpatel.designpattern.Structural.adapter;

public class SmartBulb implements SmartDevice {
    @Override
    public void turnOn() {
        System.out.println("Smart Bulb is now ON 💡");
    }

    @Override
    public void turnOff() {
        System.out.println("Smart Bulb is now OFF 💡");
    }

    @Override
    public String getName() {
        return "Smart Bulb";
    }
}
