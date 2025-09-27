package com.shivpatel.designpattern.Structural.decorator;

public class Pizza implements FoodItem {
    @Override
    public String getDescription() {
        return "Plain Pizza";
    }

    @Override
    public double getCost() {
        return 150.0;
    }
}
