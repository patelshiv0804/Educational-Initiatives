package com.shivpatel.designpattern.Structural.decorator;

public class DecoratorDemo {
    public static void main(String[] args) {
        FoodItem order = new Pizza();
        System.out.println(order.getDescription() + " => Rs." + order.getCost());

        order = new CheeseAddon(order);
        order = new FriesAddon(order);
        order = new DrinkAddon(order);

        System.out.println("Final Order: " + order.getDescription());
        System.out.println("Total Cost: Rs." + order.getCost());
    }

    public static void run() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'run'");
    }
}
