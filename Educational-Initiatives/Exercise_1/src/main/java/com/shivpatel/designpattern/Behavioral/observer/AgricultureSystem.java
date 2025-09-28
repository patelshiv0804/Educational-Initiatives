package com.shivpatel.designpattern.Behavioral.observer;


public class AgricultureSystem implements WeatherObserver {
    @Override
    public void update(float temperature, float humidity, float pressure) {
        if (humidity < 30) {
            System.out.println("[AgricultureSystem] Soil is dry. Activating irrigation system");
        } else {
            System.out.println("[AgricultureSystem] Humidity sufficient. Irrigation OFF.");
        }
    }

    @Override
    public String getName() {
        return "AgricultureSystem";
    }
}
