package com.example.cv.service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;


@Service
public class PdfService {
    @Value("${home.folder}")
    String homefolder;

    @Value("${chrome.path}")
    String chromePath;

    public File generatePdf(String newfilename) throws IOException, InterruptedException {


        Path tempPdfPath = Files.createTempFile("pdf-", ".pdf");
        File tempPdf = tempPdfPath.toFile();
        File indexHtmlTemp = new File( homefolder +newfilename+ ".html");
        File PDFpath = new File(homefolder  +newfilename+ ".pdf"); /* path that I declared */

        try {
            Process process = Runtime.getRuntime().exec(getPdfGenerationCommand( PDFpath, indexHtmlTemp));
            process.waitFor(10000, TimeUnit.MILLISECONDS);
        } catch (IOException | InterruptedException e) {
            System.out.println("e: " + e);
        } finally {
            Files.deleteIfExists(tempPdfPath);

        }

        return PDFpath;
    }

    public String getPdfGenerationCommand(File tempPdf, File indexHtmlTemp) {
        if (tempPdf == null) {
            tempPdf = new File("/root/projects/cvGenerator/src/main/resources/CVTemplates/cv.pdf");
        }

//        if (indexHtmlTemp == null) {
//            indexHtmlTemp = Paths.get("/root/projects/cvGenerator/src/main/resources/CVTemplates/cv.html");
//        }

        String chromeCommandTemplate = "--no-sandbox --headless --disable-gpu --run-all-compositor-stages-before-draw --virtual-time-budget=30000 --print-to-pdf={{printToPdf}} {{indexHtml}} --no-pdf-header-footer --print-to-pdf-no-header";
//        String chromePath = System.getenv("chromepath");

        return chromePath + " " + chromeCommandTemplate
                .replace("{{printToPdf}}", tempPdf.getAbsolutePath())
                .replace("{{indexHtml}}", indexHtmlTemp.getAbsolutePath());
    }

}