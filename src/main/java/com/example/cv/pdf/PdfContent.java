package com.example.cv.pdf;


import java.util.Map;

public class PdfContent {
    private String filename;
    private String text;
    private Map<String, Object> styleProperties; 

    public PdfContent(String filename, String text, Map<String, Object> styleProperties) {
        this.filename = filename;
        this.text = text;
        this.styleProperties = styleProperties;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Map<String, Object> getStyleProperties() {
        return styleProperties;
    }

    public void setStyleProperties(Map<String, Object> styleProperties) {
        this.styleProperties = styleProperties;
    }

}
