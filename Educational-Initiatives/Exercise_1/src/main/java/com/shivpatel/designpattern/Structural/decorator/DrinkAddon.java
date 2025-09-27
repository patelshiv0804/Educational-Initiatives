package com.shivpatel.designpattern.Structural.decorator;

public class DrinkAddon implements FoodItem {
    private final FoodItem base;

    public DrinkAddon(FoodItem base) {
        this.base = base;
    }

    @Override
    public String getDescription() {
        return base.getDescription() + " + Cold Drink 🥤";
    }

    @Override
    public double getCost() {
        return base.getCost() + 50.0;
    }
}
