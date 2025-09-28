package com.shivpatel.designpattern.Behavioral.strategy;

import java.util.Scanner;

public class StrategyDemo {
    public static void run() {
        Scanner sc = new Scanner(System.in);

        Chatbot bot = new Chatbot(new FormalResponse());

        System.out.println("=== Chatbot Reply Strategy Demo ===");
        boolean running = true;

        while (running) {
            System.out.println("\nChoose Reply Style:");
            System.out.println("1. Formal");
            System.out.println("2. Friendly");
            System.out.println("3. Technical");
            System.out.println("0. Exit");
            System.out.print("Your choice: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    bot.setStrategy(new FormalResponse());
                    break;
                case "2":
                    bot.setStrategy(new FriendlyResponse());
                    break;
                case "3":
                    bot.setStrategy(new TechnicalResponse());
                    break;
                case "0":
                    running = false;
                    continue;
                default:
                    System.out.println("Invalid choice.");
                    continue;
            }

            if (!running) break;

            System.out.print("Enter your query: ");
            String query = sc.nextLine();
            bot.reply(query);
        }

        sc.close();
        System.out.println("Chatbot demo ended.");
    }

    public static void main(String[] args) {
        run();
    }
}
