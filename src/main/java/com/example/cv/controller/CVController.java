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
            Map<String, Object> mockedDataMap = JsonToStringMapConverter.convertJsonToMap(mockdata);
            String mockedDataOpenAi = interactionToJsonService.processUserJson(mockedDataMap);



//            String mockedDataOpenAi2 = "{\n" +
//                    "  \"id\": \"chatcmpl-AXnQtkKjhFuLy3WURy2rL1hcj6DXD\",\n" +
//                    "  \"object\": \"chat.completion\",\n" +
//                    "  \"created\": 1732618207,\n" +
//                    "  \"model\": \"gpt-4o-2024-08-06\",\n" +
//                    "  \"choices\": [\n" +
//                    "    {\n" +
//                    "      \"index\": 0,\n" +
//                    "      \"message\": {\n" +
//                    "        \"role\": \"assistant\",\n" +
//                    "        \"content\": \"```json\\n{\\n  \\\"personalDetails\\\": {\\n    \\\"name\\\": \\\"\\\",\\n    \\\"email\\\": \\\"\\\",\\n    \\\"phone\\\": \\\"0522255654\\\",\\n    \\\"linkedIn\\\": \\\"https://www.linkedin.com/in/tal-moshel/\\\",\\n    \\\"gitHub\\\": \\\"https://github.com/TalMoshel1\\\",\\n    \\\"summary\\\": \\\"Experienced in JavaScript and React, aiming to become a Full Stack Developer.\\\"\\n  },\\n  \\\"jobs\\\": [\\n    {\\n      \\\"title\\\": \\\"\\\",\\n      \\\"company\\\": \\\"\\\",\\n      \\\"period\\\": \\\"\\\",\\n      \\\"responsibilities\\\": [\\\"\\\", \\\"\\\"]\\n    }, {\\n      \\\"title\\\": \\\"\\\",\\n      \\\"company\\\": \\\"\\\",\\n      \\\"period\\\": \\\"\\\",\\n      \\\"responsibilities\\\": [\\\"\\\", \\\"\\\"]\\n    }\\n  ],\\n  \\\"educations\\\": [\\n    {\\n      \\\"degree\\\": \\\"Bachelor's Degree in Computer Science\\\",\\n      \\\"institution\\\": \\\"ABC University\\\",\\n      \\\"year\\\": \\\"2022 - 2024\\\",\\n      \\\"achievements\\\": [\\n        \\\"Best student\\\"\\n      ]\\n    }\\n  ],\\n  \\\"Skills\\\": [\\\"one skill\\\", \\\"second skill\\\", \\\"third skill\\\"],\\n  \\\"preferredCvTemplate\\\": \\\"\\\",\\n  \\\"projects\\\": [\\n    {\\n      \\\"role\\\": \\\"FrontEndBackEndDeveloper\\\",\\n      \\\"project\\\": \\\"PrivateBoxingLessonsApp\\\",\\n      \\\"technologies\\\": [\\\"Express\\\", \\\"2FA\\\", \\\"nodemailer\\\", \\\"MongoDB\\\"],\\n      \\\"body\\\": \\\"long string\\\",\\n      \\\"urls\\\": {\\n        \\\"githubRepository\\\": [\\\"https://www.youtube.com/\\\", \\\"www.youtube.com\\\"],\\n        \\\"live\\\": \\\"www.google.com\\\"\\n      }\\n    }\\n  ],\\n \\\"army\\\": {\\n \\\"period\\\": \\\"2020 - 2023\\\", \\n    \\\"body\\\": \\\"Golani fighter.\\\"\\n  }\\n}\\n```\",\n" +
//                    "        \"refusal\": null\n" +
//                    "      },\n" +
//                    "      \"logprobs\": null,\n" +
//                    "      \"finish_reason\": \"stop\"\n" +
//                    "    }\n" +
//                    "  ],\n" +
//                    "  \"usage\": {\n" +
//                    "    \"prompt_tokens\": 511,\n" +
//                    "    \"completion_tokens\": 168,\n" +
//                    "    \"total_tokens\": 679,\n" +
//                    "    \"prompt_tokens_details\": {\n" +
//                    "      \"cached_tokens\": 0,\n" +
//                    "      \"audio_tokens\": 0\n" +
//                    "    },\n" +
//                    "    \"completion_tokens_details\": {\n" +
//                    "      \"reasoning_tokens\": 0,\n" +
//                    "      \"audio_tokens\": 0,\n" +
//                    "      \"accepted_prediction_tokens\": 0,\n" +
//                    "      \"rejected_prediction_tokens\": 0\n" +
//                    "    }\n" +
//                    "  },\n" +
//                    "  \"system_fingerprint\": \"fp_7f6be3efb0\"\n" +
//                    "}\n";

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> responseMap = objectMapper.readValue(mockedDataOpenAi, Map.class);

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


                JSONObject jsonObject = new JSONObject(personalDetailsJson);
                String nameProp = jsonObject.getString("name");
                String newFileName = generateFileName(nameProp);

                PersonalDetails personalDetails = gson.fromJson(personalDetailsJson, PersonalDetails.class);

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

                String templateName = "cv";
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
        return String.valueOf(cal.get(Calendar.YEAR)); 
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


