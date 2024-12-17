package com.example.cv.model;

import java.util.ArrayList;
import java.util.List;

public class PersonalDetails {
    private String name;
    private String email;
    private String phone;
    private String linkedIn;
    private String gitHub;
    private String summary;

    public PersonalDetails(String name, String email, String phone, String linkedIn, String gitHub, String summary) {
        this.name = name != null ? name : "";
        this.email = email != null ? email : "";
        this.phone = phone != null ? phone : "";
        this.linkedIn = linkedIn != null ? linkedIn : "";
        this.gitHub = gitHub != null ? gitHub : "";
        this.summary = summary != null ? summary : "";

    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public
    String getGitHub() {
        return gitHub;
    }

    public String getSummary() {
        return summary;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone
                = phone;
    }

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }

    public void setGitHub(String gitHub) {
        this.gitHub = gitHub;
    }

    public void setSummary(String
                                   summary) {
        this.summary = summary;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<section class=\"section personal-details\">");
        sb.append("<h2> ").append(getName()).append("</h2>");
        sb.append("<ul>");
        if (getPhone() != null && !getPhone().isEmpty()) {
            sb.append("<li>Cell: ").append(getPhone()).append("</li>");
        }
        if (getEmail() != null && !getEmail().isEmpty()) {
            sb.append("<li>Email: ").append(getEmail()).append("</li>");
        }
        if (getLinkedIn() != null && !getLinkedIn().isEmpty()) {
            sb.append("<li>LinkedIn: <a href=\"").append(getLinkedIn()).append("\">").append(getLinkedIn()).append("</a></li>");
        }
        if (getGitHub() != null && !getGitHub().isEmpty()) {
            sb.append("<li>GitHub: <a href=\"").append(getGitHub()).append("\">").append("</a></li>");
        }
        sb.append("<li>Summary: ").append(getSummary()).append("</li>");
        sb.append("</ul>");
        sb.append("</section>");
        return sb.toString();
    }
}
