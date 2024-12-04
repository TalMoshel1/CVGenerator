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
	}
}
