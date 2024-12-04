package com.example.cv.model;

public class Army {
    private String period;
    private String body;

    // Default constructor
    public Army() {}

    // Parameterized constructor
    public Army(String period, String body) {
        this.period = period;
        this.body = body;
    }

    // Getter for period
    public String getPeriod() {
        return period;
    }

    // Setter for period
    public void setPeriod(String period) {
        this.period = period;
    }

    // Getter for body
    public String getBody() {
        return body;
    }

    // Setter for body
    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Army{" +
                "period='" + period + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}

