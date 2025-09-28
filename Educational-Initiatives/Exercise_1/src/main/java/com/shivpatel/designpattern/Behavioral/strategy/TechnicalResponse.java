package com.shivpatel.designpattern.Behavioral.strategy;

public class TechnicalResponse implements ReplyStrategy {
    @Override
    public String generateReply(String query) {
        return "System Note: Query received -> \"" + query +
                "\". Performing diagnostics... Possible resolution will be provided soon.";
    }
}
