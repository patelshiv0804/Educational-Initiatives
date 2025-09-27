package com.shivpatel.designpattern.Structural.decorator;

public class CheeseAddon implements FoodItem {
    private final FoodItem base;

    public CheeseAddon(FoodItem base) {
        this.base = base;
    }

    @Override
    public String getDescription() {
        return base.getDescription() + " + Extra Cheese 🧀";
    }

    @Override
    public double getCost() {
        return base.getCost() + 40.0;
    }
}
