package com.example.cv.controller;
import com.example.cv.model.PersonalDetails;
import com.example.cv.model.Job;
import com.example.cv.model.Education;
import com.example.cv.service.TemplateService;
import com.example.cv.service.PdfService;
import com.example.cv.util.HtmlGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@RestController
@RequestMapping("/api/file")
public class CVController {

    private final TemplateService templateService;
    private final PdfService pdfService;
    private final HtmlGenerator htmlGenerator;

    @Autowired
    public CVController(TemplateService templateService, PdfService pdfService, HtmlGenerator htmlGenerator) {
        this.templateService = templateService;
        this.pdfService = pdfService;
        this.htmlGenerator = htmlGenerator;
    }

    /**
     * Endpoint for generating a CV PDF using the provided job and education data.
     *
     * @param requestData The data to generate the CV, including Job and Education details.
     * @param filename   The name of the generated PDF file.
     * @return Response indicating the result of PDF generation.
     */
    @PostMapping("/generate")
    public String generateCvPdf(
            @RequestBody Map<String, Object> requestData,
            @RequestParam(name = "filename", defaultValue = "generatedCV") String filename) {
        try {
            Gson gson = new Gson();
            String personalDetailsJson = gson.toJson(requestData.get("personalDetails"));

            PersonalDetails personalDetails = gson.fromJson(personalDetailsJson, PersonalDetails.class);
            // Deserialize jobs
            String jobsJson = gson.toJson(requestData.get("jobs"));
            List<Job> jobs = gson.fromJson(jobsJson, new TypeToken<List<Job>>() {}.getType());

            // Deserialize educations
            String educationsJson = gson.toJson(requestData.get("educations"));

            List<Education> educations = gson.fromJson(educationsJson, new TypeToken<List<Education>>() {}.getType());

            // Generate HTML sections for work and education and privateDetails
            String privateDetailsSection = htmlGenerator.generatePersonalInfoSection(personalDetails);
            String workSection = htmlGenerator.generateWorkSection(jobs);
            String educationSection = htmlGenerator.generateEducationSection(educations);

            // Add generated HTML sections and other data to the map
            requestData.put("workSection", workSection);
            requestData.put("educationSection", educationSection);
            requestData.put("personalDetailsSection", personalDetails);

            // Compile the HTML template with data
            String templateName = "cv"; // Template file name without extension
            String htmlContent = templateService.compileTemplateWithSections(templateName, requestData, jobs, educations, personalDetails);

            // Set output filename with .pdf extension
            String outputPath = Paths.get("src/main/resources/templates/" + filename + ".pdf").toAbsolutePath().toString();

            // Generate the PDF
            pdfService.generatePdf(htmlContent, outputPath);

            return "PDF Generated Successfully! Saved at: " + outputPath;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error generating PDF: " + e.getMessage();
        }
    }
}


