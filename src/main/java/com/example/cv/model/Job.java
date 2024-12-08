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

        // If the title, company, and responsibilities are all invalid, return an empty string
        if ((getTitle() == null || getTitle().isEmpty()) &&
                (getCompany() == null || getCompany().isEmpty()) &&
                (responsibilities == null || responsibilities.isEmpty())) {
            return "";  // Return empty string if no valid data
        }

        // Only generate title if it's valid
        if (getTitle() != null && !getTitle().isEmpty()) {
            sb.append("<h3>").append(getTitle()).append(" - ").append(getCompany());

            // Generate period if valid
            if (getPeriod() != null && !getPeriod().isEmpty()) {
                sb.append(" <span>(").append(getPeriod()).append(")</span>");
            }
            sb.append("</h3>");
        }

        // Generate responsibilities if valid
        if (responsibilities != null && !responsibilities.isEmpty()) {
            sb.append("<ul>");
            responsibilities.forEach(responsibility -> {
                if (responsibility != null && !responsibility.isEmpty()) {
                    sb.append("<li>").append(responsibility).append("</li>");
                }
            });
            sb.append("</ul>");
        }
        System.out.println("sb.toString: " + sb.toString());
        return sb.toString();
    }


}