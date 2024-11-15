package com.example.cv.controller;

import com.example.cv.model.PdfContent;
import com.example.cv.model.UserDetailsRequest;
import com.example.cv.service.ExtractPDFService;
import com.example.cv.service.PdfService;
import com.example.cv.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import java.nio.file.Paths;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import java.nio.file.Paths;
import java.util.Map;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class CVController {

    private final ExtractPDFService extractPDFService;

    @Autowired
    public CVController(ExtractPDFService extractPDFService) {
        this.extractPDFService = extractPDFService;
    }

    /**
     * Extracts PDF content based on the provided filename.
     *
     * @param filename The filename of the PDF template.
     * @param session  The HTTP session to store placeholders and extracted text.
     * @return ResponseEntity containing the PdfContent or an error message.
     */
    @GetMapping("/extract/{filename}")
    public ResponseEntity<?> extractPdfContent(@PathVariable String filename, HttpSession session) {
        try {
            PdfContent pdfContent = extractPDFService.extractTextFromPdf(filename, session);

            if (pdfContent == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("PDF content could not be extracted.");
            }

            return ResponseEntity.ok(pdfContent);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to extract PDF content: " + e.getMessage());
        }
    }

    @RestController
    @RequestMapping("/file")
    public class FileController {

        private final TemplateService templateService;
        private final PdfService pdfService;

        @Autowired
        public FileController(TemplateService templateService, PdfService pdfService) {
            this.templateService = templateService;
            this.pdfService = pdfService;
        }

        @PostMapping("/generate")
        public String generateCvPdf(
                @RequestBody Map<String, Object> requestData,
                @RequestParam(name = "filename", defaultValue = "generatedCV") String filename) {
            try {
                // Compile Handlebars Template using cv.hbt
                String templateName = "cv";  // This is the file name without the extension
                String htmlContent = templateService.compileTemplate(templateName, requestData);

                // Set output filename with .pdf extension
                String outputPath = Paths.get("src/main/resources/templates/" + filename + ".pdf").toAbsolutePath().toString();

                // Generate PDF
                pdfService.generatePdf(htmlContent, outputPath);

                return "PDF Generated Successfully! Saved at: " + outputPath;
            } catch (Exception e) {
                e.printStackTrace();
                return "Error generating PDF: " + e.getMessage();
            }
        }
    }
    @PostMapping("/submit-details")
    public ResponseEntity<String> submitDetails(@RequestBody UserDetailsRequest request, HttpSession session) {
        try {
            String filename = request.getFilename();
            Map<String, String> userDetails = request.getUserDetails();

            if (filename == null || filename.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Filename is required.");
            }

            // Extract and customize PDF content with user details
            PdfContent extractedText = extractPDFService.extractTextFromPdf(filename, session);
            if (extractedText == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Failed to extract PDF content.");
            }

            // Call the inject function from ExtractPDFService with filename and userDetails
            String outputFilePath = extractPDFService.createPdfWithUserData(filename, userDetails, session);

            if (outputFilePath == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to generate customized CV.");
            }

            return ResponseEntity.ok("Customized CV is ready at: " + outputFilePath);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing user details: " + e.getMessage());
        }
    }

    /**
     * Fetches placeholders from the session.
     *
     * @param session The HTTP session containing placeholders.
     * @return ResponseEntity with the placeholders.
     */
    @GetMapping("/session-placeholders")
    public ResponseEntity<?> getSessionPlaceholders(HttpSession session) {
        Set<String> placeholders = (Set<String>) session.getAttribute("placeholders");

        if (placeholders == null || placeholders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No placeholders found in session.");
        }

        return ResponseEntity.ok(placeholders);
    }





    @RestController
    @RequestMapping("/api/file")
    public class PdfController {

        private final TemplateService templateService;
        private final PdfService pdfService;

        @Autowired
        public PdfController(TemplateService templateService, PdfService pdfService) {
            this.templateService = templateService;
            this.pdfService = pdfService;
        }

        /**
         * Endpoint to generate a PDF from a specified Handlebars template.
         *
         * @param requestData  JSON data to inject into the template
         * @param templateName Name of the Handlebars template (without .hbs extension)
         * @return Success or error message
         */
        @PostMapping("/generate")
        public String generateFile(
                @RequestBody Map<String, Object> requestData,
                @RequestParam(name = "template") String templateName) {
            try {
                // Compile Handlebars Template with Data
                String htmlContent = templateService.compileTemplate(templateName, requestData);

                // Define the output PDF file path
                String outputFileName = templateName + ".pdf";
                String outputPath = Paths.get("src/main/resources/templates/" + outputFileName).toAbsolutePath().toString();

                // Generate PDF
                pdfService.generatePdf(htmlContent, outputPath);

                return "PDF Generated Successfully! Saved at: " + outputPath;
            } catch (Exception e) {
                e.printStackTrace();
                return "Error generating PDF: " + e.getMessage();
            }
        }
    }
    /**
     * Basic endpoint for testing connectivity.
     */
    @GetMapping("/")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello World!");
    }
}
