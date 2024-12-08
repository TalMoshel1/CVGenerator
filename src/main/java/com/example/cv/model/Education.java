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
        StringBuilder sb = new StringBuilder();
        System.out.println("education to string: "+ degree+ " "+ institution + " "+year);
        // Only include content if the degree, institution, and year are all present
        if (degree != null && !degree.isEmpty() && institution != null && !institution.isEmpty() && year != null && !year.isEmpty()) {

            // Append <h2> for EDUCATION
            sb.append("<h2>EDUCATION</h2>");

            // Start the div with class 'education'
            sb.append("<div class='education'>");

            // Append the degree in <h3> tag
            sb.append("<h3>").append(degree);

            // Append the institution before the year
            sb.append(" - ").append(institution);

            // Append year inside a <span> if it's not null or empty
            sb.append(" <span>").append(year).append("</span>");

            // Close the h3 tag
            sb.append("</h3>");

            // If achievements are present, include them as a list
            if (achievements != null && !achievements.isEmpty()) {
                sb.append("<ul>");
                for (String achievement : achievements) {
                    sb.append("<li>").append(achievement).append(".</li>");
                }
                sb.append("</ul>");
            }

            // Close the div tag
            sb.append("</div>");
        } else {
            // If any fields are missing, return an empty string (no content)
            return "";
        }

        // Return the generated HTML string
        return sb.toString();
    }





}