package com.shivpatel.designpattern.Structural.adapter;

import java.util.ArrayList;
import java.util.List;

public class AdapterDemo {
    public static void main(String[] args) {
        List<SmartDevice> devices = new ArrayList<>();
        devices.add(new SmartBulb());
        devices.add(new SmartThermostat());
        devices.add(new FanAdapter(new OldFan()));

        System.out.println("Smart Home Control Demo:");
        for (SmartDevice d : devices) {
            System.out.println("-> Controlling " + d.getName());
            d.turnOn();
            d.turnOff();
            System.out.println();
        }
    }

    public static void run() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'run'");
    }
}
