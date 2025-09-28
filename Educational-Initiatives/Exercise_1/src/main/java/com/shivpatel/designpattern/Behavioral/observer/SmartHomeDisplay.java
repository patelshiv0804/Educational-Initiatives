package com.shivpatel.designpattern.Behavioral.observer;

public class SmartHomeDisplay implements WeatherObserver {
    @Override
    public void update(float temperature, float humidity, float pressure) {
        if (temperature > 30) {
            System.out.println("[SmartHomeDisplay] It's hot. Turning ON AC.");
        } else if (temperature < 10) {
            System.out.println("[SmartHomeDisplay] It's cold. Turning ON heater.");
        } else {
            System.out.println("[SmartHomeDisplay] Comfortable temperature. No action needed.");
        }
    }

    @Override
    public String getName() {
        return "SmartHomeDisplay";
    }
}
