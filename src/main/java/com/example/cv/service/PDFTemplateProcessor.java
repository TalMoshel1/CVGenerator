//package com.example.cv.service;
//
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.text.PDFTextStripper;
//import org.apache.pdfbox.pdmodel.PDPageContentStream;
//import org.springframework.stereotype.Service;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.URL;
//import java.util.Map;
//
//@Service
//public class PDFTemplateProcessor {
//
//    public String extractAndReplaceText(String filename, Map<String, String> replacements) throws IOException {
//        URL resource = getClass().getResource("/CVTemplates/" + filename + ".pdf");
//
//        if (resource == null) {
//            throw new IllegalArgumentException("File not found: " + filename + ".pdf");
//        }
//
//        try (InputStream inputStream = resource.openStream();
//             PDDocument document = PDDocument.load(inputStream)) {
//
//            PDFTextStripper pdfTextStripper = new PDFTextStripper();
//            String text = pdfTextStripper.getText(document);
//
//            for (Map.Entry<String, String> entry : replacements.entrySet()) {
//                text = text.replace("{" + entry.getKey() + "}", entry.getValue());
//            }
//
//            // To write back into PDF, you'd need a different approach here
//            saveUpdatedText(document, text);
//
//            return text; // For testing purposes, you can return the modified text
//        } catch (IOException e) {
//            throw new IOException("Failed to process PDF content for: " + filename, e);
//        }
//    }
//
//    private void saveUpdatedText(PDDocument document, String text) throws IOException {
//        PDPageContentStream contentStream = null;
//        try {
//            // Add logic to write updated text back to the document here
//            // contentStream = new PDPageContentStream(document, ...);
//        } finally {
//            if (contentStream != null) {
//                contentStream.close();
//            }
//            document.save("Updated_" + document.getDocumentInformation().getTitle() + ".pdf");
//            document.close();
//        }
//    }
//}
