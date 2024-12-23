package com.example.cv.model;

import java.util.ArrayList;
import java.util.List;

public class Education {

    private String degree;
    private String institution;
    private String year;
    private List<String> achievements;

    public Education(String degree, String institution, String year, List<String> achievements) {
        this.degree = degree != null ? degree : "";
        this.institution = institution != null ? institution : "";
        this.year = year != null ? year : "";
        this.achievements = achievements != null ? achievements : new ArrayList<>();
    }

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (degree != null && !degree.isEmpty() && institution != null && !institution.isEmpty()) {



            sb.append("<div class='education'>");

            sb.append("<h3>").append(degree);

            sb.append(" - ").append(institution);

            if (year != null && !year.trim().isEmpty()) {
                sb.append(" <span> (").append(year).append(")</span>");
            }

            sb.append("</h3>");

            if (achievements != null && !achievements.isEmpty()) {
                sb.append("<ul>");
                for (String achievement : achievements) {
                    if (achievement != null && !achievement.trim().isEmpty()) {
                        sb.append("<li>").append(achievement).append("</li>");
                    }
                }
                sb.append("</ul>");
            }

            sb.append("</div>");
        } else {
            return "";
        }


        return sb.toString();
    }





}