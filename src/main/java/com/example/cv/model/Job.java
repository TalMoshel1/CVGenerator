package com.example.cv.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Job {
    private String title;
    private String company;
    private String period;
    private List<String> responsibilities;

    // Constructor with optional arguments
    public Job(String title, String company, String period, List<String> responsibilities) {
        this.title = title != null ? title : "";
        this.company = company != null ? company : "";
        this.period = period != null ? period : "";
        this.responsibilities = responsibilities != null ? responsibilities : new ArrayList<>();
    }

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public List<String> getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(List<String> responsibilities) {
        this.responsibilities = responsibilities;
    }

    @Override
    public String toString() {
        return "<h3>" + (getTitle() != null ? getTitle() : "No Title") + "</h3>" +
                "<p>" + (getCompany() != null ? getCompany() : "No Company") + " - (" +
                (getPeriod() != null && !getPeriod().isEmpty() ? getPeriod() : "No Period") + ")</p>" +
                "<ul>" +
                responsibilities.stream()
                        .map(responsibility -> "<li>" + responsibility + "</li>")
                        .collect(Collectors.joining()) +
                "</ul>";
    }
}