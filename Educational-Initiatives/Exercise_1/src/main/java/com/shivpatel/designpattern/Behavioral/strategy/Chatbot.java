package com.shivpatel.designpattern.Behavioral.strategy;

public class Chatbot {
    private ReplyStrategy strategy;

    public Chatbot(ReplyStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(ReplyStrategy strategy) {
        if (strategy != null) {
            this.strategy = strategy;
        }
    }

    public void reply(String query) {
        if (query == null || query.isEmpty()) {
            System.out.println("Chatbot: Please enter a valid question.");
            return;
        }
        System.out.println("Chatbot: " + strategy.generateReply(query));
    }
}
