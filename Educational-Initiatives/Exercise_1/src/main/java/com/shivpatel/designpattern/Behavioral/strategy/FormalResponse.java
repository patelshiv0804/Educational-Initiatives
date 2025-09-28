package com.shivpatel.designpattern.Behavioral.strategy;

public class FormalResponse implements ReplyStrategy {
    @Override
    public String generateReply(String query) {
        return "Dear Customer, thank you for reaching out. We have received your query: \"" +
                query + "\". Our support team will assist you shortly.";
    }
}
