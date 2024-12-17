package com.example.cv.service;

import com.example.cv.model.PersonalDetails;
import com.example.cv.util.HtmlGenerator;
import com.example.cv.model.Job;
import com.example.cv.model.Education;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Service
public class TemplateService {

    private final Handlebars handlebars;
    private final HtmlGenerator htmlGenerator;

    @Autowired
    public TemplateService(HtmlGenerator htmlGenerator) {
        this.handlebars = new Handlebars();
        this.htmlGenerator = htmlGenerator;

        handlebars.registerHelper("dateFormat", (context, options) -> {
            String format = options.param(0, "yyyy-MM-dd");
            try {
                java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern(format);
                java.time.LocalDate date = java.time.LocalDate.parse(context.toString());
                return date.format(formatter);
            } catch (Exception e) {
                return context.toString();
            }
        });
    }


    public String compileTemplateWithSections(String templateName, Map<String, Object> data, List<Job> jobs, List<Education> educations, PersonalDetails personalDetails, String skills, String projects, String armyDetailsSection) throws Exception {


        data.put("workSection", jobs);
        data.put("educationSection", educations);
        data.put("personalDetailsSection", personalDetails);
        data.put("skillsSection", skills);
        data.put("projectsSection", projects);
        data.put("armySection", armyDetailsSection);





        return compileTemplate(templateName, data);
    }



    public String compileTemplate(String templateName, Map<String, Object> data) throws IOException {
        String templatePath = "/CVandHtmltemplates/" + templateName + ".hbt";

        try (InputStream inputStream = getClass().getResourceAsStream(templatePath)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Template file not found: " + templatePath);
            }

            String templateContent = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            Template template = handlebars.compileInline(templateContent);
            String renderedTemplate = template.apply(data);
            return renderedTemplate;
        }
    }


    public void saveHtmlFile(String htmlContent, String outputPath) throws Exception {
        java.nio.file.Path path = java.nio.file.Path.of(outputPath);
        java.nio.file.Files.writeString(path, htmlContent);
    }
}
