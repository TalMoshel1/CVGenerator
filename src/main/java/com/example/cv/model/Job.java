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
        StringBuilder sb = new StringBuilder();

        // Only process valid title
        if (getTitle() == null || getTitle().isEmpty()) {
            return ""; // No content if the title is invalid
        }

        // Start building the <h3> tag with title and company
        sb.append("<h3>").append(getTitle());
        if (getCompany() != null && !getCompany().isEmpty()) {
            sb.append(" - ").append(getCompany());
        }

        // Add the period within a <span> tag if it exists
        if (getPeriod() != null && !getPeriod().isEmpty()) {
            sb.append(" <span>(").append(getPeriod()).append(")</span>");
        }
        sb.append("</h3>");

        // Add company and period within a <p> element (if applicable)

        // Add responsibilities if valid
        if (responsibilities != null && !responsibilities.isEmpty()) {
            sb.append("<ul>");
            responsibilities.forEach(responsibility -> {
                if (responsibility != null && !responsibility.isEmpty()) {
                    sb.append("<li>").append(responsibility).append("</li>");
                }
            });
            sb.append("</ul>");
        }
        System.out.println("job sb: "+ sb.toString());
        return sb.toString();
    }




}