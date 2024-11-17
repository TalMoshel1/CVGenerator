package com.example.cv.util;
import com.example.cv.model.PersonalDetails;

import com.example.cv.model.Job;
import com.example.cv.model.Education;
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
            if (personalDetails.getLinkedIn() != null && !personalDetails.getLinkedIn().isEmpty()) {
                personalInfoSection.append("<li>LINKEDIN: <a href=\"").append(personalDetails.getLinkedIn()).append("\">").append(personalDetails.getLinkedIn()).append("</a></li>");
            }

            // Append GitHub profile if present
            if (personalDetails.getGitHub() != null && !personalDetails.getGitHub().isEmpty()) {
                personalInfoSection.append("<li>GITHUB: <a href=\"https://github.com/").append(personalDetails.getGitHub()).append("\">").append(personalDetails.getGitHub()).append("</a></li>");
            }

            // Append summary

            personalInfoSection.append("</ul>");
            personalInfoSection.append("<p>SUMMARY: ").append(personalDetails.getSummary()).append("</p>");

            personalInfoSection.append("</section>");
        }

        return personalInfoSection.toString();
    }

    /**
     * Generates the HTML for the work (job) section.
     */
    public String generateWorkSection(List<Job> jobs) {
        StringBuilder workSection = new StringBuilder();
        for (Job job : jobs) {
            System.out.println("Generating work section for job: " + job.getTitle());
            workSection.append("<div class='job'>")
                    .append("<h3>")
                    .append(job.getTitle() != null ? job.getTitle() : "Title Missing")
                    .append("</h3>")
                    .append("<p>").append(job.getCompany()).append(" - (").append(job.getPeriod()).append(")</p>")
                    .append("<ul>");
            for (String responsibility : job.getResponsibilities()) {
                workSection.append("<li>").append(responsibility).append("</li>");
            }
            workSection.append("</ul></div>");
        }
        System.out.println("Generated work section HTML: " + workSection.toString());  // Debugging line
        return workSection.toString();
    }


    /**
     * Generates the HTML for the education section.
     */
    public String generateEducationSection(List<Education> educations) {
        StringBuilder educationSection = new StringBuilder();

        for (Education education : educations) {
            // Log the education object to check its contents before processing
            System.out.println("Processing education: " + education);  // This will print the Education object

            // Start building the HTML only if there's meaningful data to display
            educationSection.append("<div class='education'>");

            // Only append the degree if it's not empty or null
            if (education.getDegree() != null && !education.getDegree().isEmpty()) {
                educationSection.append("<h3>").append(education.getDegree()).append("</h3>");
            }

            // Append the institution and year if they're not empty or null
            if ((education.getInstitution() != null && !education.getInstitution().isEmpty()) ||
                    (education.getYear() != null && !education.getYear().isEmpty())) {
                educationSection.append("<p>")
                        .append(education.getInstitution() != null ? education.getInstitution() : "")
                        .append(" - ")
                        .append(education.getYear() != null ? education.getYear() : "")
                        .append("</p>");
            }

            // Only append the list of achievements if they exist
            if (education.getAchievements() != null && !education.getAchievements().isEmpty()) {
                educationSection.append("<ul>");
                for (String achievement : education.getAchievements()) {
                    educationSection.append("<li>").append(achievement).append("</li>");
                }
                educationSection.append("</ul>");
            }

            educationSection.append("</div>");
        }

        return educationSection.toString();
    }
}
