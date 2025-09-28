package com.shivpatel.designpattern.Behavioral.observer;

public class ObserverDemo {
    public static void run() {
        WeatherStation station = new WeatherStation();

        station.addObserver(new MobileAppDisplay("Shiv"));
        station.addObserver(new AgricultureSystem());
        station.addObserver(new SmartHomeDisplay());

        System.out.println("=== Weather Station Demo ===");
        station.setMeasurements(35.0f, 25.0f, 1012.0f);
        station.setMeasurements(15.0f, 60.0f, 1008.0f);
        station.setMeasurements(8.0f, 40.0f, 1005.0f);
    }

    public static void main(String[] args) {
        run();
    }
}
