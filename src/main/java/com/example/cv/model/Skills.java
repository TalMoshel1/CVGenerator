package com.example.cv.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Skills {

    private List<String> skills; // Use List<String> for skills

    // Constructor (optional)
    public Skills() {
        this.skills = new ArrayList<>(); // Initialize empty list
    }

    // Getters and setters for skills
    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    // Add a method to add individual skills
    public void addSkill(String skill) {
        skills.add(skill);
    }

    @Override
    public String toString() {
        if (skills.isEmpty()) {
            return "No skills listed.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("<ul>");
        for (String skill : skills) {
            sb.append("<li>").append(skill).append("</li>");
        }
        sb.append("</ul>");
        return sb.toString();
    }
}
