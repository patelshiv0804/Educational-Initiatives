package com.shivpatel.designpattern.Structural.decorator;

public class FriesAddon implements FoodItem {
    private final FoodItem base;

    public FriesAddon(FoodItem base) {
        this.base = base;
    }

    @Override
    public String getDescription() {
        return base.getDescription() + " + Fries 🍟";
    }

    @Override
    public double getCost() {
        return base.getCost() + 70.0;
    }
}
