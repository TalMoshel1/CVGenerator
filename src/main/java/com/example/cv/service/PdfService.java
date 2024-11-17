package com.example.cv.service;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.OutputStream;

@Service
public class PdfService {

    /**
     * Generates a PDF from HTML content.
     *
     * @param htmlContent HTML content to convert
     * @param outputPath  Path where the PDF will be saved
     * @throws Exception if PDF generation fails
     */
    public void generatePdf(String htmlContent, String outputPath) throws Exception {
//        System.out.println("html: "+ htmlContent);
//        try (OutputStream os = new FileOutputStream(outputPath)) {
//            PdfRendererBuilder builder = new PdfRendererBuilder();
//            builder.useFastMode();
//            builder.withHtmlContent(htmlContent, null);
//            builder.toStream(os);
//            builder.run();
//        }
    }
}