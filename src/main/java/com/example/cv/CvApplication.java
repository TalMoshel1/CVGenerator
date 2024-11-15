package com.example.cv;

import com.example.cv.pdf.CustomPDFTextStripper;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@SpringBootApplication
public class CvApplication {

	public static void main(String[] args) {
		SpringApplication.run(CvApplication.class, args);

		// PDF processing logic
		try {
			// Load the PDF file
			File file = new File("src/main/resources/CVTemplates/test.pdf");
			PDDocument document = PDDocument.load(file);

			// Process the PDF with CustomPDFTextStripper
			CustomPDFTextStripper pdfTextStripper = new CustomPDFTextStripper();
			pdfTextStripper.getText(document); // This triggers the processing

			// Retrieve and print style properties
			Map<String, Object> styleProperties = pdfTextStripper.getStyleProperties();
			System.out.println("Extracted Style Properties:");
			for (Map.Entry<String, Object> entry : styleProperties.entrySet()) {
				System.out.println("Text: " + entry.getKey() + " | Properties: " + entry.getValue());
			}

			// Close the document
			document.close();

		} catch (IOException e) {
			System.err.println("Error processing PDF: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
