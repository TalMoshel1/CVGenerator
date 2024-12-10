//package com.example.cv.pdf;
//
//
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.URL;
//
//@Service
//public class PdfExtractorService {
//
//    public PdfContent extractTextWithStyle(String filename) throws IOException {
//        URL resource = getClass().getResource("/CVTemplates/" + filename + ".pdf");
//
//        if (resource == null) {
//            throw new IllegalArgumentException("File not found: " + filename + ".pdf");
//        }
//
//        try (InputStream inputStream = resource.openStream();
//             PDDocument document = PDDocument.load(inputStream)) {
//
//            CustomPDFTextStripper textStripper = new CustomPDFTextStripper();
//            textStripper.setSortByPosition(true);
//
//            String extractedText = textStripper.getText(document);
//            PdfContent pdfContent = new PdfContent(filename, extractedText, textStripper.getStyleProperties());
//
//            return pdfContent;
//        }
//    }
//}
