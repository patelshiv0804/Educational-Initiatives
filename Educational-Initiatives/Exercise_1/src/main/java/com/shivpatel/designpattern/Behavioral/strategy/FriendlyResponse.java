package com.shivpatel.designpattern.Behavioral.strategy;

public class FriendlyResponse implements ReplyStrategy {
    @Override
    public String generateReply(String query) {
        return "Hey there! Thanks for asking: \"" + query +
                "\". Don’t worry, I’ll help you out in no time!";
    }
}
