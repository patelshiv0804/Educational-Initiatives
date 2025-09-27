package com.shivpatel.designpattern.Creational.factory;

public interface DatabaseConnection {
    void connect();
    void disconnect();
    String getDescription();
}
