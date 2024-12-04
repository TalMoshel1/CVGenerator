//package com.example.cv.controller;
//
//
//import com.theokanning.openai.OpenAiService;
//import com.theokanning.openai.completion.CompletionChoice;
//import com.theokanning.openai.completion.CompletionRequest;
//import com.theokanning.openai.completion.CompletionResult;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class CVDataBuilder {
//
//    private Map<String, Object> collectJobDetails(int jobNumber) {
//        Map<String, Object> jobDetails = new HashMap<>();
//        jobDetails.put("title", askUser("What is the title of job " + jobNumber + "?"));
//        // ... (Ask for other details like company, period, responsibilities)

//        return jobDetails;
//    }
//
//    private Map<String, Object> collectEducationDetails(int educationNumber) {
//        Map<String, Object> educationDetails = new HashMap<>();
//        educationDetails.put("degree", askUser("What is the degree for education " + educationNumber + "?"));
//        // ... (Ask for other details like institution, year, achievements)
//        return educationDetails;
//    }
//
//    private final OpenAiService service;
//
//    public CVDataBuilder(OpenAiService service) {
//        this.service = service;
//    }
//
//    public static Map<String, Object> buildCVData() {
//        Map<String, Object> cvData = new HashMap<>();
//
//        // Personal Details
//        cvData.put("personalDetails", collectPersonalDetails());
//
//        // Jobs
//        List<Map<String, Object>> jobs = new ArrayList<>();
//        int numJobs = Integer.parseInt(askUser("How many jobs do you want to add?"));
//        for (int i = 0; i < numJobs; i++) {
//            jobs.add(collectJobDetails(i + 1));
//        }
//        cvData.put("jobs", jobs);
//
//        // Educations
//        List<Map<String, Object>> educations = new ArrayList<>();
//        int numEducations = Integer.parseInt(askUser("How many educations do you want to add?"));
//        for (int i = 0; i < numEducations; i++) {
//            educations.add(collectEducationDetails(i + 1));
//        }
//        cvData.put("educations", educations);
//
//        return cvData;
//    }
//
//    private Map<String, Object> collectPersonalDetails() {
//        Map<String, Object> personalDetails = new HashMap<>();
//        personalDetails.put("name", askUser("What is your full name?"));
//        // Use a more specific prompt to get the email address
//        personalDetails.put("email", askUser("Please provide your professional email address."));
//        // ... (Collect other details like phone number, LinkedIn, GitHub, summary, etc.)
//    if (personalDetails.get("name") == null || personalDetails.get("email") == null) {
//            // Handle missing information, e.g., ask the user again or provide an error message
//            System.out.println("Please provide all required personal details.");
//            return collectPersonalDetails(); // Recursive call to ask again
//            }
//        return personalDetails;
//    }
//
//    // ... (Implement other collect methods with validation)
//
//    private String askUser(String question) {
//        // Implement error handling and validation for the user response
//        try {
//            CompletionRequest request = CompletionRequest.builder()
//                    .model("text-davinci-003")
//                    .prompt(question)
//                    .maxTokens(100)
//                    .temperature(0.7)
//                    .build();
//
//            CompletionResult response = service.createCompletion(request);
//            CompletionChoice choice = response.getChoices().get(0);
//            return choice.getText().trim();
//        } catch (Exception e) {
//            // Handle exceptions and provide user feedback
//            System.out.println("Error fetching response from OpenAI: " + e.getMessage());
//            return "";
//        }
//    }
//}