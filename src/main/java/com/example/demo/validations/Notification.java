package com.example.demo.validations;

public class Notification {
    private String property;
    private String message;

    public Notification() {
    }

    public Notification(String property, String message) {
        this.property = property;
        this.message = message;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "property='" + property + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
