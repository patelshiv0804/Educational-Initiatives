package com.shivpatel.designpattern.Behavioral.observer;

public class MobileAppDisplay implements WeatherObserver {
    private final String user;

    public MobileAppDisplay(String user) {
        this.user = user;
    }

    @Override
    public void update(float temperature, float humidity, float pressure) {
        System.out.printf("[MobileApp - %s] Temp: %.1f°C, Humidity: %.1f%%, Pressure: %.1f hPa%n",
                user, temperature, humidity, pressure);
    }

    @Override
    public String getName() {
        return "MobileAppDisplay for " + user;
    }
}
