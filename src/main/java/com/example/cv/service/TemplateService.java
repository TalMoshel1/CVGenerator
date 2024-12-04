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

        // Register custom helpers or decorators if needed
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

    /**
     * Compiles a Handlebars template with the given data.
     */
//    public String compileTemplate(String templateName, Map<String, Object> data) throws Exception {
//        // Load the template file from the resources
//        String templatePath = "/CVandHtmltemplates/" + templateName + ".hbt";
//        InputStream inputStream = getClass().getResourceAsStream(templatePath);
//
//        if (inputStream == null) {
//            throw new IllegalArgumentException("Template file not found: " + templatePath);
//        }
//
//        // Read the template content
//        String templateContent;
//        try (Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name())) {
//            templateContent = scanner.useDelimiter("\\A").next(); // Read the entire content
//        }
//
//        // Compile the template
//        Template template = handlebars.compileInline(templateContent);
//        System.out.println("template service: "+template.apply(data));
//        return template.apply(data);
//    }

    public String compileTemplate(String templateName, Map<String, Object> data) throws IOException {
        System.out.println("data: "+ data);
        String templatePath = "/CVandHtmltemplates/" + templateName + ".hbt";

        // Load the template file
        try (InputStream inputStream = getClass().getResourceAsStream(templatePath)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Template file not found: " + templatePath);
            }

            // Read the template content
            String templateContent = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            // Compile and apply the template
            Template template = handlebars.compileInline(templateContent);
            String renderedTemplate = template.apply(data);

            // Debugging output

            return renderedTemplate;
        }
    }
    /**
     * Saves the generated HTML content to a file.
     */
    public void saveHtmlFile(String htmlContent, String outputPath) throws Exception {
        java.nio.file.Path path = java.nio.file.Path.of(outputPath);
        java.nio.file.Files.writeString(path, htmlContent);
    }
}
