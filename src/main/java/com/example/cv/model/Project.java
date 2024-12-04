package com.example.cv.model;


import java.util.List;

public class Project {

    private String role;
    private String project;
    private List<String> technologies;
    private String body;
    private Urls urls;

    // Getters and Setters
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public List<String> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(List<String> technologies) {
        this.technologies = technologies;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Urls getUrls() {
        return urls;
    }

    public void setUrls(Urls urls) {
        this.urls = urls;
    }

    // Inner class for URLs
    public static class Urls {
        private List<String> githubRepository;
        private String live;

        // Getters and Setters
        public List<String> getGithubRepository() {
            return githubRepository;
        }

        public void setGithubRepository(List<String> githubRepository) {
            this.githubRepository = githubRepository;
        }

        public String getLive() {
            return live;
        }

        public void setLive(String live) {
            this.live = live;
        }
    }
}
