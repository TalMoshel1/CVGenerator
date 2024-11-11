//package com.example.cv.service;
//
//import com.example.cv.model.CVData;
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//import java.util.Map;
//import org.apache.pdfbox.Loader;
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.pdmodel.PDPage;
//import org.apache.pdfbox.pdmodel.PDPageContentStream;
//import org.apache.pdfbox.pdmodel.font.PDType1Font;
//import org.apache.pdfbox.text.PDFTextStripper;
//
//public class CVService {
//
//    public void populateTemplate(String fileName, CVData data) throws IOException {
//        // Load the PDF template using Loader
//        File file = new File("src/main/resources/CVTemplates/" + fileName);
//        PDDocument document = Loader.loadPDF(file);
//
//        // Locate and replace placeholders in the PDF file
//        for (PDPage page : document.getPages()) {
//            PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true);
//
//            // Personal Information
//            writeText(contentStream, data.getPersonalInfo().get("name"), 100, 700);
//            writeText(contentStream, data.getPersonalInfo().get("email"), 100, 680);
//            writeText(contentStream, data.getPersonalInfo().get("phone"), 100, 660);
//            writeText(contentStream, data.getPersonalInfo().get("city"), 100, 640);
//            writeText(contentStream, data.getPersonalInfo().get("profession"), 100, 620);
//
//            // Professional Experience
//            int yPosition = 580;
//            for (Map<String, String> experience : data.getProExperience()) {
//                writeText(contentStream, experience.get("name") + " (" + experience.get("years") + " years)", 100, yPosition);
//                yPosition -= 20;
//                writeText(contentStream, experience.get("description"), 100, yPosition);
//                yPosition -= 40;
//            }
//
//            // Education
//            for (Map<String, String> education : data.getEducation()) {
//                writeText(contentStream, education.get("university_college_name") + " - " + education.get("field") + " (" + education.get("years") + ")", 100, yPosition);
//                yPosition -= 20;
//                writeText(contentStream, "Skills Learned: " + education.get("skills_learned"), 100, yPosition);
//                yPosition -= 40;
//            }
//
//            contentStream.close();
//        }
//
//        // Save the updated document
//        document.save("src/main/resources/CVTemplates/Updated_" + fileName);
//        document.close();
//    }
//
//    private void writeText(PDPageContentStream contentStream, String text, float x, float y) throws IOException {
//        contentStream.beginText();
//        contentStream.setFont(PDType1Font.HELVETICA, 12);
//        contentStream.newLineAtOffset(x, y);
//        contentStream.showText(text);
//        contentStream.endText();
//    }
//}