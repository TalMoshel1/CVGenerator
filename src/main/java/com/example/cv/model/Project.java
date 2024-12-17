package com.example.cv.model;


import java.util.List;

public class Project {

    private String role;
    private String project;
    private List<String> technologies;
    private String body;
    private Urls urls;

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

    @Override
    public String toString() {
        if (project == null || project.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();

        sb.append("<div class='project'>");

        if (!project.isEmpty()) {
            sb.append("<div class='first-line'>")
                    .append("<h2>").append(project).append("</h2>");

            if (technologies != null && !technologies.isEmpty()) {
                sb.append(" - ").append("<span class='tech-wraper'>");
                for (String tech : technologies) {
                    sb.append("<span>").append(tech).append("</span> ");
                }
                sb.append("</span>");
            }

            sb.append("</div>");
        }

        if (urls != null) {
            if (urls.getGithubRepository() != null && !urls.getGithubRepository().isEmpty()) {
                for (String repo : urls.getGithubRepository()) {
                    String absoluteRepoUrl = repo.startsWith("http://") || repo.startsWith("https://") ? repo : "http://" + repo;
                    sb.append("<a href='").append(absoluteRepoUrl).append("' target='_blank'>")
                            .append(repo).append("</a>")
                            .append("<span class='blank'> </span>");
                }
            }
            if (urls.getLive() != null && !urls.getLive().isEmpty()) {
                String absoluteLiveUrl = urls.getLive().startsWith("http://") || urls.getLive().startsWith("https://")
                        ? urls.getLive()
                        : "http://" + urls.getLive();
                sb.append("<a href='").append(absoluteLiveUrl).append("' target='_blank'>Live Project</a>");
            }
        }

        if (body != null && !body.isEmpty()) {
            sb.append("<p>").append(body).append("</p>");
        }

        sb.append("</div>");


        return sb.toString();
    }

}
