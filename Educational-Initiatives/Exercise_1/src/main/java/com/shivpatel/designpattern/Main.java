package com.shivpatel.designpattern;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== Design Pattern Demo Menu ===");
            System.out.println("1. Creational - Factory Pattern");
            System.out.println("2. Creational - Singleton Pattern");
            System.out.println("3. Structural - Adapter Pattern (Smart Home Devices)");
            System.out.println("4. Structural - Decorator Pattern (Food Ordering)");
            System.out.println("0. Exit");
            System.out.print("Choose option: ");
            String input = sc.nextLine().trim();

            switch (input) {
                case "1":
                    com.shivpatel.designpattern.Creational.factory.DatabaseConnectionFactoryDemo.run();
                    break;
                case "2":
                    com.shivpatel.designpattern.Creational.singleton.SingletonDemo.run();
                    break;
                 case "3":
                    com.shivpatel.designpattern.Structural.adapter.AdapterDemo.run();
                    break;
                case "4":
                    com.shivpatel.designpattern.Structural.decorator.DecoratorDemo.run();
                    break;
                case "0":
                    running = false;
                    System.out.println("Exiting Demo. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 1, 2, 3 , 4 or 0.");
            }
        }

        sc.close();
    }
}
