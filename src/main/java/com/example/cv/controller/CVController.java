package com.example.cv.controller;

import com.example.cv.service.ExtractPDFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class CVController {

    private final ExtractPDFService extractPDFService;

    // Constructor to inject the ExtractPDFService
    @Autowired
    public CVController(ExtractPDFService extractPDFService) {
        this.extractPDFService = extractPDFService;
    }

    /**
     * Endpoint to extract text from a PDF template.
     * This method will only extract the content from the provided PDF without performing any placeholder replacement.
     *
     * @param filename The name of the PDF file (without extension).
     * @return The extracted text from the PDF.
     */
    @GetMapping("/extract/{filename}")
    public ResponseEntity<String> extractPdfContent(@PathVariable String filename) {
        try {
            // Extract text from the PDF (no replacement happens here)
            String extractedText = extractPDFService.extractTextFromPdf(filename);
            return new ResponseEntity<>(extractedText, HttpStatus.OK);
        } catch (IOException e) {
            // In case of an error, send a 500 response
            return new ResponseEntity<>("Failed to extract PDF content", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint for receiving user input details (e.g., name, role, email) and replacing the placeholders.
     * The user details should be sent as JSON in the request body.
     *
     * @param userDetails The user details (e.g., name, email, role, etc.) to replace in the CV.
     * @return The customized CV with placeholders replaced by the user-provided details.
     */
    @PostMapping("/submit-details")
    public ResponseEntity<String> submitDetails(@RequestBody Map<String, String> userDetails) {
        try {
            // Extract the filename from the user details (if passed)
            System.out.println("DFKJHSDF");
            String filename = userDetails.get("filename");

            if (filename == null) {
                return new ResponseEntity<>("Filename is required", HttpStatus.BAD_REQUEST);
            }

            // Extract the PDF text first (without replacement)
            String extractedText = extractPDFService.extractTextFromPdf(filename);

            // Replace placeholders with the user-provided details
            String customizedCV = extractPDFService.replacePlaceholders(extractedText, userDetails);

            // Return the customized CV
            return new ResponseEntity<>(customizedCV, HttpStatus.OK);
        } catch (IOException e) {
            // In case of an error, send a 500 response
            return new ResponseEntity<>("Error processing user details", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * A simple endpoint to check if the API is up and running.
     */
    @GetMapping("/")
    public ResponseEntity<String> hello() {
        return new ResponseEntity<>("Hello World!", HttpStatus.OK);
    }
}
