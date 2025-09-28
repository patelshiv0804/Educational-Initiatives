package com.shivpatel.designpattern.Behavioral.observer;

import java.util.ArrayList;
import java.util.List;

public class WeatherStation {
    private final List<WeatherObserver> observers = new ArrayList<>();
    private float temperature;
    private float humidity;
    private float pressure;

    public void addObserver(WeatherObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void removeObserver(WeatherObserver observer) {
        observers.remove(observer);
    }

    public void setMeasurements(float temperature, float humidity, float pressure) {
        if (temperature < -100 || temperature > 100) {
            throw new IllegalArgumentException("Temperature out of range");
        }
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        notifyObservers();
    }

    private void notifyObservers() {
        for (WeatherObserver observer : observers) {
            observer.update(temperature, humidity, pressure);
        }
    }
}
