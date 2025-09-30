package com.shivpatel.smarthome.hub ;

public class Trigger {
    private final String parameterName;
    private final String operator;
    private final double threshold;
    private final String action;

    public Trigger(String parameterName, String operator, double threshold, String action) {
        this.parameterName = parameterName;
        this.operator = operator;
        this.threshold = threshold;
        this.action = action;
    }

    public String getParameterName() {
        return parameterName;
    }

    public String getOperator() {
        return operator;
    }

    public double getThreshold() {
        return threshold;
    }

    public String getAction() {
        return action;
    }

    @Override
    public String toString() {
        return String.format("{param:%s %s %.2f -> action:\"%s\"}", parameterName, operator, threshold, action);
    }
}
