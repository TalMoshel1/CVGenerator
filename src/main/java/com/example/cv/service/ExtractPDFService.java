package com.example.cv.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ExtractPDFService {

    // Temporary in-memory storage for placeholders mapping
    private final Map<String, Set<String>> placeholdersMap = new HashMap<>();

    /**
     * Extract text from a PDF and store placeholders for later use.
     * @param filename The name of the file (without extension).
     * @return Extracted text with placeholders.
     * @throws IOException if there's an issue reading the PDF.
     */
    public String extractTextFromPdf(String filename) throws IOException {
        // Use resource loading to access the PDF from the resources folder
        URL resource = getClass().getResource("/CVTemplates/" + filename + ".pdf");

        if (resource == null) {
            throw new IllegalArgumentException("File not found: " + filename + ".pdf");
        }

        try (InputStream inputStream = resource.openStream();
             PDDocument document = PDDocument.load(inputStream)) {

            // Extract text from the loaded PDF document
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            String pdfText = pdfTextStripper.getText(document);

            // Detect and store placeholders
            Set<String> placeholders = detectPlaceholders(pdfText);
            placeholdersMap.put(filename, placeholders); // Store placeholders for later use

            return pdfText;
        } catch (IOException e) {
            throw new IOException("Failed to extract content from PDF: " + filename, e);
        }
    }

    /**
     * Detect placeholders (e.g., {Full Name}, {Role}) in the text.
     * @param text The extracted PDF text.
     * @return A set of placeholder names.
     */
    private Set<String> detectPlaceholders(String text) {
        Set<String> placeholders = new HashSet<>();
        Pattern pattern = Pattern.compile("\\{(\\w+)}");  // Pattern to match {name}
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            placeholders.add(matcher.group(1));  // Add the placeholder name (without curly braces)
        }
        return placeholders;
    }

    /**
     * Replace placeholders in the extracted PDF text with the user-provided values.
     * @param text The extracted text.
     * @param replacementMap A map of placeholder names and their corresponding replacement values.
     * @return The text with placeholders replaced.
     */
    public String replacePlaceholders(String text, Map<String, String> replacementMap) {
        for (Map.Entry<String, String> entry : replacementMap.entrySet()) {
            String placeholder = "\\{" + entry.getKey() + "}"; // Match {placeholder}
            text = text.replaceAll(placeholder, entry.getValue());
        }
        return text;
    }

    /**
     * Get the stored placeholders for a given filename.
     * @param filename The filename for which to retrieve placeholders.
     * @return A set of placeholders, or null if none are stored for the given filename.
     */
    public Set<String> getPlaceholdersForFile(String filename) {
        return placeholdersMap.get(filename);
    }
}
