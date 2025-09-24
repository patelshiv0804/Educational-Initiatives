package com.shivpatel.designpattern.creation.factory;

public interface DatabaseConnection {
    void connect();
    void disconnect();
    String getDescription();
}
