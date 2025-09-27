package com.shivpatel.designpattern.Structural.adapter;

public class FanAdapter implements SmartDevice {
    private final OldFan fan;

    public FanAdapter(OldFan fan) {
        this.fan = fan;
    }

    @Override
    public void turnOn() {
        fan.startSpinning();
    }

    @Override
    public void turnOff() {
        fan.stopSpinning();
    }

    @Override
    public String getName() {
        return "Legacy Fan (adapted)";
    }
}
