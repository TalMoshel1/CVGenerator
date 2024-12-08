package com.example.cv.util;
import com.example.cv.model.*;

import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class HtmlGenerator {

    public String generatePersonalInfoSection(PersonalDetails personalDetails) {
        StringBuilder personalInfoSection = new StringBuilder();

        if (personalDetails != null) {
            personalInfoSection.append("<section class=\"section personal-details\">");
            personalInfoSection.append("<h2>").append(personalDetails.getName()).append("</h2>");
            personalInfoSection.append("<ul>");

            // Append phone number if present
            if (personalDetails.getPhone() != null && !personalDetails.getPhone().isEmpty()) {
                personalInfoSection.append("<li>CELL: ").append(personalDetails.getPhone()).append("</li>");
            }

            // Append email address if present
            if (personalDetails.getEmail() != null && !personalDetails.getEmail().isEmpty()) {
                personalInfoSection.append("<li>EMAIL: ").append(personalDetails.getEmail()).append("</li>");
            }

            // Append LinkedIn profile if present
            // Check if LinkedIn URL is provided and ensure it has a scheme (add 'http://' if missing)
            if (personalDetails.getLinkedIn() != null && !personalDetails.getLinkedIn().isEmpty()) {
                String linkedInUrl = personalDetails.getLinkedIn();
                // Check if the URL doesn't start with 'http://' or 'https://', and prepend 'http://'
                if (!linkedInUrl.startsWith("http://") && !linkedInUrl.startsWith("https://")) {
                    linkedInUrl = "http://" + linkedInUrl;  // Add http:// if no scheme is present
                }
                personalInfoSection.append("<li>LINKEDIN: <a href=\"").append(linkedInUrl).append("\">").append(linkedInUrl).append("</a></li>");
            }

// Check if GitHub URL is provided and ensure it has a scheme (add 'http://' if missing)
            if (personalDetails.getGitHub() != null && !personalDetails.getGitHub().isEmpty()) {
                String gitHubUrl = personalDetails.getGitHub();
                System.out.println("gitHubUrl: "+ gitHubUrl);
                // Check if the URL doesn't start with 'http://' or 'https://', and prepend 'http://'
                if (!gitHubUrl.startsWith("http://") && !gitHubUrl.startsWith("https://")) {
                    gitHubUrl = "http://" + gitHubUrl;  // Add http:// if no scheme is present
                }
                personalInfoSection.append("<li>GITHUB: <a href=\"").append(gitHubUrl).append("\">").append(gitHubUrl).append("</a></li>");
            }

            // Append summary

            personalInfoSection.append("</ul>");
            personalInfoSection.append("<p>SUMMARY: ").append(personalDetails.getSummary()).append("</p>");

            personalInfoSection.append("</section>");
        }

        return personalInfoSection.toString();
    }

    public String generateSummarySection(PersonalDetails personalDetails) {
        StringBuilder summerySection = new StringBuilder();
        if (personalDetails != null) {
            if (personalDetails.getSummary() != null) {
                summerySection.append("<section class='section summary'>")
                        .append("<h2>SUMMARY</H2>")
                        .append("<p>")
                        .append(personalDetails.getSummary())
                        .append("</p>")
                        .append("</section>");
            }
        }
        return summerySection.toString();
    }


    public String generateWorkSection(List<Job> jobs) {
        StringBuilder workSection = new StringBuilder();
        for (Job job : jobs) {
            System.out.println("Generating work section for job: " + job.getTitle());
            workSection.append("<div class='job'>")
                    .append("<h3>")
                    .append(job.getTitle() != null ? job.getTitle() : "Title Missing")
                    .append("</h3>")
                    .append("<p>").append(job.getCompany()).append("<span class='year'>").append(" - (").append(job.getPeriod()).append("</span>").append(")</p>")
                    .append("<ul>");
            for (String responsibility : job.getResponsibilities()) {
                if (!responsibility.isEmpty()) {
                    workSection.append("<li>").append(responsibility).append(".</li>");
                }
            }
            workSection.append("</ul></div>");
        }
        return workSection.toString();
    }


    /**
     * Generates the HTML for the education section.
     */

    public String generateEducationSection(List<Education> educations) {
        StringBuilder educationSection = new StringBuilder();

        if (educations != null && !educations.isEmpty()) {
            educationSection.append("<h2>EDUCATION</h2>");
        }

        for (Education education : educations) {
            educationSection.append("<div class='education'>");

            if (education.getDegree() != null && !education.getDegree().isEmpty()) {
                System.out.println("get degree: "+ education.getDegree());
                educationSection.append("<h3>").append(education.getDegree());

                if (education.getYear() != null && !education.getYear().isEmpty()) {

                    educationSection.append(" <span>").append(education.getYear()).append("</span>");

                }

                educationSection.append("</h3>");
            }

            // Append achievements if available
            if (education.getAchievements() != null && !education.getAchievements().isEmpty()) {
                educationSection.append("<ul>");
                for (String achievement : education.getAchievements()) {
                    educationSection.append("<li>").append(achievement).append(".</li>");
                }
                educationSection.append("</ul>");
            }

            educationSection.append("</div>");
        }

        return educationSection.toString();
    }
    public String generateSkillsSection(List<?> skills) {

        StringBuilder skillsSection = new StringBuilder();

        System.out.println("skills: " + skills);

        // Check if the list is not empty and contains at least one non-empty skill
        boolean hasValidSkills = skills != null && skills.stream()
                .anyMatch(skill -> skill instanceof String && !((String) skill).isEmpty());

        if (hasValidSkills) {
            skillsSection.append("<h2>SKILLS</h2>");
            skillsSection.append("<p>");
            for (Object skill : skills) {
                if (skill instanceof String) {
                    String skillString = (String) skill;
                    if (!skillString.isEmpty()) {
                        skillsSection.append(skillString).append(". ");
                    }
                } else {
                    // Handle other data types as needed, e.g., extract string values
                    String skillString = String.valueOf(skill); // Or use specific conversion logic
                    skillsSection.append(skillString).append(". ");
                }
            }
            skillsSection.append("</p>");
        }


        return skillsSection.toString();
    }

    public String generateProjectSection(List<Project> projects) {
        StringBuilder projectSection = new StringBuilder();

        System.out.println("projects input: "+projects.toString());

        // Check if the projects list is not empty
        if (projects != null && projects.size() > 0 && !projects.isEmpty() && projects.get(0) != null && projects.get(0).getProject() != null && !projects.get(0).getProject().isBlank()) {
            projectSection.append("<h2 class='PROJECTS'>PROJECTS</h2>");  // Add the "PROJECTS" header
        }

        for (Project project : projects) {
            projectSection.append("<div class='project'>");

            if (project.getProject() != null && !project.getProject().isEmpty()) {
                projectSection.append("<div class='first-line'>")
                        .append("<h2>").append(project.getProject()).append(" ").append("</h2>");

                if (project.getTechnologies() != null && !project.getTechnologies().isEmpty()) {
                    projectSection.append(" - ").append("<span class='tech-wraper'>");
                    for (String tech : project.getTechnologies()) {
                        projectSection.append("<span>").append(tech).append("</span> ");
                    }
                    projectSection.append("</span>");
                }
            }

            Project.Urls urls = project.getUrls();
            if (urls != null) {
                if (urls.getGithubRepository() != null && !urls.getGithubRepository().isEmpty()) {
                    // Iterate through each GitHub repository
                    for (String repo : urls.getGithubRepository()) {
                        String absoluteRepoUrl = repo.startsWith("http://") || repo.startsWith("https://") ? repo : "http://" + repo;

                        projectSection.append("<a href='").append(absoluteRepoUrl).append("' target='_blank'>")
                                .append(repo).append(" </a>").append("<span class='blank'> </span>");
                    }
                }

                // Check if the live URL is not null or empty
                if (urls.getLive() != null && !urls.getLive().isEmpty()) {
                    String absoluteLiveUrl = urls.getLive().startsWith("http://") || urls.getLive().startsWith("https://")
                            ? urls.getLive()
                            : "http://" + urls.getLive();

                    projectSection.append("<a href='").append(absoluteLiveUrl).append("' target='_blank'>Live Project</a>");
                }

                projectSection.append("</div>");
            }

            if (project.getBody() != null && !project.getBody().isEmpty()) {
                projectSection.append("<p>").append(project.getBody()).append("</p>");
            }

            projectSection.append("</div>");
        }

        System.out.println("projectSection: " + projectSection);

        return projectSection.toString();
    }

    public String generateArmySection(Army armyDetails) {
        StringBuilder armySection = new StringBuilder();
        if (!armyDetails.getBody().isEmpty() || !armyDetails.getPeriod().isEmpty()) {

            armySection.append("<section class='section army'>").append("<div class='one-line-army'>").append("<h2>").append("MILITARY SERVICE").append("</h2>");
            if (armyDetails.getPeriod() != null && !armyDetails.getPeriod().isEmpty()) {
                armySection.append("<span>(").append(armyDetails.getPeriod()).append(")</span>");
            }
            armySection.append("</div>");
            if (armyDetails.getBody() != null && !armyDetails.getBody().isEmpty()) {
                armySection.append("<div class='body'>").append(armyDetails.getBody()).append("</div>");

            }
            armySection.append("</section>");
        }

        return armySection.toString();

    }


}

