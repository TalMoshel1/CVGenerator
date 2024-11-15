package com.example.cv.service;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Scanner;

@Service
public class TemplateService {

    private final Handlebars handlebars;

    public TemplateService() {
        this.handlebars = new Handlebars();
        // Register custom helpers or decorators if needed
        handlebars.registerHelper("dateFormat", (context, options) -> {
            // Assuming the date is a String in ISO format
            String format = options.param(0, "yyyy-MM-dd");
            // Use java.time for date formatting
            try {
                java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern(format);
                java.time.LocalDate date = java.time.LocalDate.parse(context.toString());
                return date.format(formatter);
            } catch (Exception e) {
                return context.toString(); // Fallback to original string if parsing fails
            }
        });
    }

    /**
     * Compiles a Handlebars template with the given data.
     *
     * @param templateName Name of the template file (without .hbs extension)
     * @param data         Data to inject into the template
     * @return Compiled HTML content
     * @throws Exception if template reading or compilation fails
     */
    public String compileTemplate(String templateName, Map<String, Object> data) throws Exception {
        // Use getClass().getResource to load the template from the resources/templates directory
        String templatePath = "/templates/" + templateName + ".hbt";
        InputStream inputStream = getClass().getResourceAsStream(templatePath);

        if (inputStream == null) {
            throw new IllegalArgumentException("Template file not found: " + templatePath);
        }

        // Read the template file content from the InputStream
        String templateContent;
        try (Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name())) {
            templateContent = scanner.useDelimiter("\\A").next(); // Read the entire content
        }

        // Compile the template using Handlebars
        Template template = handlebars.compileInline(templateContent);
        return template.apply(data);
    }

    /**
     * Saves HTML content to a file.
     *
     * @param htmlContent HTML content to save
     * @param outputPath  Path where the HTML file will be saved
     * @throws Exception if file writing fails
     */
    public void saveHtmlFile(String htmlContent, String outputPath) throws Exception {
        java.nio.file.Path path = java.nio.file.Path.of(outputPath);
        java.nio.file.Files.writeString(path, htmlContent);
    }
}
