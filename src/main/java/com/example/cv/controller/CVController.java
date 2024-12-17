package com.example.cv.controller;
import com.example.cv.helpers.JsonToStringMapConverter;
import com.example.cv.model.*;
import com.example.cv.service.InteractionToJson;
import com.example.cv.service.TemplateService;
import com.example.cv.service.PdfService;
import com.example.cv.util.HtmlGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Year;
import java.util.*;

import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/file")
public class CVController {

    private final TemplateService templateService;
    private final PdfService pdfService;
    private final HtmlGenerator htmlGenerator;

    @Autowired
    public CVController(TemplateService templateService, PdfService pdfService, HtmlGenerator htmlGenerator) {
        this.templateService = templateService;
        this.pdfService = pdfService;
        this.htmlGenerator = htmlGenerator;
    }

    static class CvGenerationData {
        public String  myGoal;
        public String myHighlights;

        public String myExperience;

        public String myEducations;

        public String myTemplate;

    }
    @Autowired
    private InteractionToJson interactionToJsonService;

    @Value("${home.folder}")
    String homeFolder;

    @PostMapping("/generate")
    public ResponseEntity<?>generateCvPdf(
            @RequestBody CvGenerationData userInput
    ) {
        JsonObject mockdata = new JsonObject();
        mockdata.addProperty("goal", userInput.myGoal);
        mockdata.addProperty("highlight", userInput.myHighlights);
        mockdata.addProperty("jobs", userInput.myExperience);
        mockdata.addProperty("educations", userInput.myEducations);
        mockdata.addProperty("template", userInput.myTemplate);


        try {
            Gson gson = new Gson();
            Map<String, Object> mockedDataMap = JsonToStringMapConverter.convertJsonToMap(mockdata); // used to convert the data recieved JSON to map but Not neccessary probably
            System.out.println("mockedData: "+ mockedDataMap);
            String mockedDataOpenAi2 = interactionToJsonService.processUserJson(mockedDataMap);
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> responseMap = objectMapper.readValue(mockedDataOpenAi2, Map.class);

            if (responseMap.containsKey("choices") && !((List) responseMap.get("choices")).isEmpty()) {
                Map<String, Object> firstChoice = (Map<String, Object>) ((List) responseMap.get("choices")).get(0);
                Map<String, Object> message = (Map<String, Object>) firstChoice.get("message");
                String aiResultContent = (String) message.get("content");

                // Remove the ```json and ending ```
                if (aiResultContent != null) {
                    aiResultContent = aiResultContent.replaceFirst("^```json\\n", "").replaceFirst("\\n```$", "");
                }


                Map<String, Object> requestData = gson.fromJson(aiResultContent, new TypeToken<Map<String, Object>>() {
                }.getType());



                String personalDetailsJson = gson.toJson(requestData.get("personalDetails"));
                String preferredCvTemplate = (String) requestData.get("preferredCvTemplate");


                System.out.println("preferredCvTemplate: " + preferredCvTemplate);
                JSONObject jsonObject = new JSONObject(personalDetailsJson);
                String nameProp = jsonObject.getString("name");
                String newFileName = generateFileName(nameProp);

                PersonalDetails personalDetails = gson.fromJson(personalDetailsJson, PersonalDetails.class);

                // Deserialize jobs
                String jobsJson = gson.toJson(requestData.get("jobs"));
                List<Job> jobs = gson.fromJson(jobsJson, new TypeToken<List<Job>>() {
                }.getType());

                String educationsJson = gson.toJson(requestData.get("educations"));


                String SkillsJson = gson.toJson(requestData.get("Skills"));

                String projectsJson = gson.toJson(requestData.get("projects"));

                String armyJson = gson.toJson(requestData.get("army"));

                Army armyDetails = gson.fromJson(armyJson, Army.class);


                List<Education> educations = gson.fromJson(educationsJson, new TypeToken<List<Education>>() {
                }.getType());

                List<Project> projects = gson.fromJson(projectsJson, new TypeToken<List<Project>>() {
                }.getType());

                List<String> skills = gson.fromJson(SkillsJson, new TypeToken<List<String>>() {
                }.getType());
                // Generate HTML sections for work and education and privateDetails
                String armySection = htmlGenerator.generateArmySection(armyDetails);
                String privateDetailsSection = htmlGenerator.generatePersonalInfoSection(personalDetails);
                String workSection = htmlGenerator.generateWorkSection(jobs);
                String educationSection = htmlGenerator.generateEducationSection(educations);
                String skillsSection = htmlGenerator.generateSkillsSection(skills);
                String projectsSection = htmlGenerator.generateProjectSection(projects);

                requestData.put("workSection", workSection);
                requestData.put("educationSection", educationSection);
                requestData.put("personalDetailsSection", personalDetails);
                requestData.put("skillsSection", skillsSection);
                requestData.put("projectsSection", projectsSection);
                requestData.put("armySection", armySection);



                String templateName = "cv"; // Template file name without extension
                String htmlContent = templateService.compileTemplateWithSections(preferredCvTemplate, requestData, jobs, educations, personalDetails, skillsSection, projectsSection, armySection);
                File htmlFile = new File(homeFolder + newFileName + ".html");
                String outputPath = htmlFile.getAbsolutePath();

                Files.write(Paths.get(outputPath), htmlContent.getBytes());

                File pdfFile = pdfService.generatePdf(newFileName);




                if (!pdfFile.exists()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
                }
                InputStream in = new FileInputStream(pdfFile);
                InputStreamResource resource = new InputStreamResource(in);
                return ResponseEntity.ok()
                        .header("Content-disposition", "attachment; filename=" + newFileName +".pdf")
                        .header("Access-Control-Expose-Headers", "*")
                        .contentLength(pdfFile.length())
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(resource);


            }


        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("CV Couldn't be generated");
    }

        public static String getThisCurrentYear() {
        Calendar cal = Calendar.getInstance();
        return String.valueOf(cal.get(Calendar.YEAR)); // Get current year
    }

    public static String generateFileName(String namesString) {
        String[] names = namesString.split(" ");
        StringBuilder fileName = new StringBuilder();

        for (String name : names) {
            fileName.append(name).append("_");
        }

        fileName.deleteCharAt(fileName.length() - 1);

        fileName.append("_Resume_").append(Year.now().getValue())
                .append("_").append(UUID.randomUUID().toString());
        return fileName.toString();
    }






}


