package com.example.cv.model;

import java.util.List;
import java.util.Map;

public class CVData {
    private Map<String, String> personalInfo;
    private List<Map<String, String>> proExperience;
    private List<Map<String, String>> education;

    public Map<String, String> getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(Map<String, String> personalInfo) {
        this.personalInfo = personalInfo;
    }

    public List<Map<String, String>> getProExperience() {
        return proExperience;
    }

    public void setProExperience(List<Map<String, String>> proExperience) {
        this.proExperience = proExperience;
    }

    public List<Map<String, String>> getEducation() {
        return education;
    }

    public void setEducation(List<Map<String, String>> education) {
        this.education = education;
    }
}

