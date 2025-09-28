package com.shivpatel.designpattern.Structural.decorator;

public class DecoratorDemo {
    public static void main(String[] args) {
        run(); // call run for standalone execution
    }

    public static void run() {
        FoodItem order = new Pizza();
        System.out.println(order.getDescription() + " => Rs." + order.getCost());

        order = new CheeseAddon(order);
        order = new FriesAddon(order);
        order = new DrinkAddon(order);

        System.out.println("Final Order: " + order.getDescription());
        System.out.println("Total Cost: Rs." + order.getCost());
    }
}
