package com.example.cv.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomPDFTextStripper extends PDFTextStripper {

    private final Map<String, Object> styleProperties = new HashMap<>();

    public CustomPDFTextStripper() throws IOException {
        super();
    }

    @Override
    protected void processTextPosition(TextPosition text) {
        Map<String, Object> textStyle = new HashMap<>();
        textStyle.put("text", text.getUnicode());
        textStyle.put("font", text.getFont().getName());
        textStyle.put("fontSize", text.getFontSizeInPt());
        textStyle.put("positionX", text.getXDirAdj());
        textStyle.put("positionY", text.getYDirAdj());

        // Attempt to access color information if available
        try {
            PDColor color = getGraphicsState().getNonStrokingColor();
            textStyle.put("color", color != null ? color.toString() : "N/A");
        } catch (Exception e) {
            textStyle.put("color", "Unknown"); // Fallback if color isn't available
        }

        styleProperties.put(text.getUnicode(), textStyle);
    }

    public Map<String, Object> getStyleProperties() {
        return styleProperties;
    }
}
