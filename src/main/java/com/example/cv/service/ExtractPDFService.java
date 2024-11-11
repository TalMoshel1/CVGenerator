package com.example.cv.service;

import com.example.cv.model.PdfContent;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class ExtractPDFService {

    public PdfContent extractTextFromPdf(String filename, HttpSession session) throws IOException {
        if (filename == null || filename.isEmpty()) {
            throw new IllegalArgumentException("Filename cannot be null or empty");
        }

        // Load the template PDF from the resources folder
        URL resource = getClass().getResource("/CVTemplates/" + filename + ".pdf");

        if (resource == null) {
            throw new IllegalArgumentException("File not found: " + filename + ".pdf");
        }

        try (InputStream inputStream = resource.openStream();
             PDDocument document = PDDocument.load(inputStream)) {

            // Extract text from the first page
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            String pdfText = pdfTextStripper.getText(document);

            // Detect placeholders based on the filename
            Set<String> placeholders = detectPlaceholders(filename);

            // Store extracted content in session
            if (session != null) {
                session.setAttribute("placeholders", placeholders);
                session.setAttribute("pdfText", pdfText);
            }

            return new PdfContent(filename, pdfText);

        } catch (IOException e) {
            throw new IOException("Failed to extract content from PDF: " + filename, e);
        }
    }

    private Set<String> detectPlaceholders(String filename) {
        Set<String> placeholders = new HashSet<>();

        if ("two".equals(filename)) {
            placeholders.add("{fullName}");
            placeholders.add("{email}");
            placeholders.add("{phoneNumber}");
            placeholders.add("{website}");
            placeholders.add("{companyName}");
            placeholders.add("{companyLocation}");
            placeholders.add("{companyStartDate}");
            placeholders.add("{companyEndDate}");
            placeholders.add("{role}");
            placeholders.add("{collegeName}");
            placeholders.add("{graduationYear}");
            placeholders.add("{degree}");
            placeholders.add("{gpa}");
            placeholders.add("{skills}");
            placeholders.add("{languages}");
        }

        return placeholders;
    }

    // Method to inject text into the PDF and save it to /resources/UsersCV
    public String injectAnswersToPdf(String pdfTemplatePath, Map<String, String> userDetails) throws IOException {
        // Define the output path to store the generated PDF
        String outputPath = "src/main/resources/UsersCV/generated_cv.pdf";

        // Ensure the directory exists
        File outputDir = new File("src/main/resources/UsersCV");
        if (!outputDir.exists()) {
            outputDir.mkdirs(); // Create the directory if it doesn't exist
        }

        // Load the existing PDF document
        PDDocument document = PDDocument.load(new java.io.File(pdfTemplatePath));

        // Create a new page or get the existing page (assuming there's only one)
        PDPage page = document.getPage(0);

        // Create a ContentStream to draw on the page
        PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true);

        // Set font and size for text
        PDFont font = PDType1Font.HELVETICA_BOLD;
        float fontSize = 12;

        // Start a new text object in the content stream
        contentStream.beginText();
        contentStream.setFont(font, fontSize);

        // Starting position for the text (x and y coordinates)
        float x = 100;
        float y = 700;  // Adjust as per your needs

        // Replace placeholders with actual user details
        String text = extractTextFromPdf(pdfTemplatePath, null).getPdfText();
        for (Map.Entry<String, String> entry : userDetails.entrySet()) {
            String placeholder = "{" + entry.getKey() + "}";
            text = text.replace(placeholder, entry.getValue());
        }

        // Split the text into lines and inject them
        String[] lines = text.split(" ");  // Adjust based on your use case

        // Loop through each line and display it at a new line with adjusted vertical position
        for (String line : lines) {
            contentStream.newLineAtOffset(x, y);  // Adjust vertical offset for next line
            contentStream.showText(line);
            y -= 15;  // Adjust the vertical position for the next line of text (line height)
        }

        // End the text object
        contentStream.endText();

        // Close the content stream
        contentStream.close();

        // Save the document with the injected text to a new file
        document.save(outputPath);
        document.close();

        return outputPath;
    }
}
