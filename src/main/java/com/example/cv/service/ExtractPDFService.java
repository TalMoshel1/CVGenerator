package com.example.cv.service;

import com.example.cv.helpers.StringTest;
import com.example.cv.model.PdfContent;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Map;
import java.util.Set; // Added this import for Set
import java.util.UUID;
import javax.servlet.http.HttpSession;

@Service
public class ExtractPDFService {

    // Method to load the PDF from the resources folder
    public PdfContent extractTextFromPdf(String filename, HttpSession session) throws IOException {
        if (filename == null || filename.isEmpty()) {
            throw new IllegalArgumentException("Filename cannot be null or empty");
        }

        // Load the template PDF from the resources folder using the absolute path starting with "/"
        URL resource = getClass().getResource("/CVTemplates/" + filename + ".pdf");

        if (resource == null) {
            // Error: file not found
            throw new IllegalArgumentException("File not found: " + filename + ".pdf");
        }

        try (InputStream inputStream = resource.openStream();
             PDDocument document = PDDocument.load(inputStream)) {

            // Extract text from the first page of the PDF
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            String pdfText = pdfTextStripper.getText(document); // This is where the getText() method is called

            // Detect placeholders based on the filename
            Set<String> placeholders = detectPlaceholders(filename);

            // Store extracted content in session (optional)
            if (session != null) {
                session.setAttribute("placeholders", placeholders);
                session.setAttribute("pdfText", pdfText);
            }

            // Return the extracted content
            return new PdfContent(filename, pdfText);

        } catch (IOException e) {
            // Error during reading the PDF file
            throw new IOException("Failed to extract content from PDF: " + filename, e);
        }
    }

    // Replace placeholders in the extracted text with user details
    public String replacePlaceholdersWithUserData(String extractedText, Map<String, String> userDetails) {
        // Remove existing line breaks to avoid issues with Helvetica font
        extractedText = extractedText.replace("\n", "<NEWLINE>").replace("\r", "<NEWLINE>");

        StringBuilder result = new StringBuilder(extractedText);  // Using StringBuilder for efficient string modification

        // Replace placeholders with user data and add new lines after each replacement
        for (Map.Entry<String, String> entry : userDetails.entrySet()) {
            String placeholder = entry.getKey();
            String replacement = entry.getValue();

            // Replace the placeholder with the replacement value
            int index = result.indexOf(placeholder);
            if (index != -1) {
                result.replace(index, index + placeholder.length(), replacement);
//                result.append("\n");
            }
        }

        // Convert StringBuilder back to string
        System.out.println("extractedText: " + result.toString());

        return result.toString();
    }

    // Create a new PDF with modified content
    public String createPdfWithUserData(String filename, Map<String, String> userDetails, HttpSession session) throws IOException {
        // Step 1: Extract the PDF content
        PdfContent pdfContent = extractTextFromPdf(filename, session);

        System.out.println("pdf content: " + pdfContent);

        // Step 2: Replace placeholders in the extracted text with user data
        String modifiedText = replacePlaceholdersWithUserData(pdfContent.getPdfText(), userDetails);

        // Step 3: Create a new PDF with the modified text
        PDDocument newDocument = new PDDocument();
        PDPage page = new PDPage();
        newDocument.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(newDocument, page);

        // Load the LiberationSans-Regular font
        PDType0Font font = PDType0Font.load(newDocument, new File("src/main/resources/fonts/LiberationSans-Regular.ttf"));

        // Adding the modified text to the PDF
        contentStream.beginText();
        contentStream.setFont(font, 12); // Set to use LiberationSans-Regular
        contentStream.newLineAtOffset(100, 750); // Set text position
        contentStream.showText(modifiedText); // Write the modified text to the PDF
        contentStream.endText();
        contentStream.close();

        // Step 4: Generate a unique file name using UUID
        String uniqueFileName = UUID.randomUUID().toString() + ".pdf";

        // Save the new PDF to the CVTemplates folder inside the resources directory
        String savePath = Paths.get("src", "main", "resources", "CVTemplates", uniqueFileName).toString();

        // Step 5: Save the document to the generated path
        newDocument.save(savePath);
        newDocument.close();

        // Return the file path or any other confirmation
        return "PDF saved at: " + savePath;
    }
    // Example method to detect placeholders from filename (just a placeholder for your actual logic)
    private Set<String> detectPlaceholders(String filename) {
        Set<String> placeholders = new HashSet<>();

        // Check if filename is "two" and set placeholders accordingly
        if ("two".equals(filename)) {
            placeholders.add("Full Name");
            placeholders.add("yourname@example.com");
            placeholders.add("(123) 456‐7890");
            placeholders.add("www.example.com");
            placeholders.add("Company");
            placeholders.add("location");
            placeholders.add("Month Year");
            placeholders.add("Role");
            placeholders.add("Describe what you did and what your impact was");
            placeholders.add("Graduation year");
            placeholders.add("Bachelor of Arts, Degree, GPA");
            placeholders.add("Relevant course work");
            placeholders.add("Programming languages");
            placeholders.add("Computer software/ frameworks");
            placeholders.add("Languages");
        }

        return placeholders;
    }
}
