package com.example.cv.controller;

import com.example.cv.model.PdfContent;
import com.example.cv.service.ExtractPDFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    /**
     * Accepts user details and injects them into the PDF.
     *
     * @param userDetails A map of user details for placeholders.
     * @param session     HTTP session with potential placeholders set during extraction.
     * @return ResponseEntity with the status of PDF generation.
     */
    @PostMapping("/submit-details")
    public ResponseEntity<String> submitDetails(@RequestBody Map<String, String> userDetails, HttpSession session) {
        try {
            String filename = userDetails.get("filename");

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
            String outputFilePath = extractPDFService.injectAnswersToPdf(filename, userDetails);

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

    /**
     * Basic endpoint for testing connectivity.
     */
    @GetMapping("/")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello World!");
    }
}
