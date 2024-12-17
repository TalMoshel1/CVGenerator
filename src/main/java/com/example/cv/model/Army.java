package com.example.cv.model;

public class Army {
    private String period;
    private String body;

    public Army() {}

    public Army(String period, String body) {
        this.period = period;
        this.body = body;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getBody() {
        return body;
    }

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

