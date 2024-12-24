package com.example.cv.model;

import java.util.List;

public class RequestData {
    public PersonalDetails getPersonalDetails() {
        return personalDetails;
    }

    public void setPersonalDetails(PersonalDetails personalDetails) {
        this.personalDetails = personalDetails;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    public List<Education> getEducations() {
        return educations;
    }

    public void setEducations(List<Education> educations) {
        this.educations = educations;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public String getPreferredCvTemplate() {
        return preferredCvTemplate;
    }

    public void setPreferredCvTemplate(String preferredCvTemplate) {
        this.preferredCvTemplate = preferredCvTemplate;
    }

    private PersonalDetails personalDetails;
    private List<Job> jobs;
    private List<Education> educations;
    private List<String> skills;
    private String preferredCvTemplate;

    public RequestData(PersonalDetails personalDetails, List<Job> jobs, List<Education> educations, List<String> skills, String preferredCvTemplate) {
        this.personalDetails = personalDetails;
        this.jobs = jobs;
        this.educations = educations;
        this.skills = skills;
        this.preferredCvTemplate = preferredCvTemplate;
    }
}
