package com.example.cv.model;

public class PdfContent {
    private String filename;
    private String pdfText;

    public PdfContent(String filename, String pdfText) {
        this.filename = filename;
        this.pdfText = pdfText;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getPdfText() {
        return pdfText;
    }

    public void setPdfText(String pdfText) {
        this.pdfText = pdfText;
    }
}
