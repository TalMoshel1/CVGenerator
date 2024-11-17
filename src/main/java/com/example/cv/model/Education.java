package com.example.cv.model;

import java.util.ArrayList;
import java.util.List;

public class Education {

    private String degree;
    private String institution;
    private String year;
    private List<String> achievements;

    // Constructor with optional arguments
    public Education(String degree, String institution, String year, List<String> achievements) {
        this.degree = degree != null ? degree : "";
        this.institution = institution != null ? institution : "";
        this.year = year != null ? year : "";
        this.achievements = achievements != null ? achievements : new ArrayList<>();
    }

    // Getters and setters
    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<String> getAchievements() {
        return achievements;
    }

    public void setAchievements(List<String> achievements) {
        this.achievements = achievements;
    }

    // Override toString() method to display education information
    @Override
    public String toString() {
        return "<h3>" + getDegree() + " - " + getInstitution() + " (" + getYear() + ")</h3>" +
                (getAchievements().isEmpty() ? "" : // Check if list is empty
                        "<ul>" +
                                getAchievements().stream().map(achievement -> "<li>" + achievement + "</li>").reduce("", (a, b) -> a + b) +
                                "</ul>");
    }
}