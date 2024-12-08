package com.example.cv.service;
import org.springframework.beans.factory.annotation.Value;


import com.example.cv.openAI.ChatGPTRequest;
import com.example.cv.openAI.ChatGptResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Service
public class InteractionToJson {

    private static final String OPENAI_URL = "https://api.openai.com/v1/chat/completions";
    private static final String OPENAI_KEY = System.getenv("OPENAI_KEY");

//    @Value("${api.key}")
//    private static String apiKey;




    public static String processUserJson(Map<String, Object> userData) {
        try {
            // Serialize Map to JSON string
            ObjectMapper objectMapper = new ObjectMapper();
            String userInputJson = objectMapper.writeValueAsString(userData);

            String apiKey = System.getenv("OPENAI_KEY");

            String prompt = "You are a CV writer expert. Please take the information you got from the user: " +
                    userInputJson +
                    ", and then translate it to the structure of this JSON object. Note: do not add information in case that the UserData missing information that will be relevant to the next JSON properties, and return the result of this long JSON structure with the new information: {\n" +
                    "  \"personalDetails\": {\n" +
                    "    \"name\": \"\",\n" +
                    "    \"email\": \"johndoe@example.com\",\n" +
                    "    \"phone\": \"123-456-7890\",\n" +
                    "    \"linkedIn\": \"https://www.linkedin.com/in/johndoe\",\n" +
                    "    \"gitHub\": \"https://github.com/johndoe\",\n" +
                    "    \"summary\": \"Experienced software engineer with a passion for building innovative solutions.\"\n" +
                    "  },\n" +
                    "  \"jobs\": [\n" +
                    "    {\n" +
                    "      \"title\": \"Senior Software Engineer\",\n" +
                    "      \"company\": \"Acme Corporation\",\n" +
                    "      \"period\": \"Jan 2020 - Present\",\n" +
                    "      \"responsibilities\": [\n" +
                    "        \"Led the development of a new feature\",\n" +
                    "        \"Optimized performance of critical components\",\n" +
                    "        \"Mentored junior team members\"\n" +
                    "      ]\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"title\": \"Software Engineer\",\n" +
                    "      \"company\": \"Tech Solutions\",\n" +
                    "      \"period\": \"Jan 2018 - Dec 2019\",\n" +
                    "      \"responsibilities\": [\n" +
                    "        \"Developed backend services using Node.js and Express.js\",\n" +
                    "        \"Collaborated with frontend team to build user interfaces\"\n" +
                    "      ]\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"educations\": [\n" +
                    "    {\n" +
                    "      \"degree\": \"Master of Science in Computer Science\",\n" +
                    "      \"institution\": \"Stanford University\",\n" +
                    "      \"year\": \"2018\",\n" +
                    "      \"achievements\": [\n" +
                    "        \"Published research paper in top-tier conference\",\n" +
                    "        \"Received academic scholarship\"\n" +
                    "      ]\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"degree\": \"Bachelor of Science in Computer Science\",\n" +
                    "      \"institution\": \"MIT\",\n" +
                    "      \"year\": \"2016\",\n" +
                    "      \"achievements\": [\n" +
                    "        \"Dean's List\",\n" +
                    "        \"Participated in hackathons\"\n" +
                    "      ]\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"Skills\": [\"SHout\"],\n" +
                    "  \"preferredCvTemplate\": \"1\",\n" +
                    "  \"projects\": [\n" +
                    "    {\n" +
                    "      \"role\": \"FrontEndBackEndDeveloper\",\n" +
                    "      \"project\": \"PrivateBoxingLessonsApp\",\n" +
                    "      \"technologies\": [\"Express\", \"2FA\", \"nodemailer\", \"MongoDB\"],\n" +
                    "      \"body\": \"long string\",\n" +
                    "      \"urls\": {\n" +
                    "        \"githubRepository\": [\"URL_here\", \"URL_here\"],\n" +
                    "        \"live\": \"URL_here\"\n" +
                    "      }\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"army\": {\n" +
                    "    \"period\": \"2020 - 2023\",\n" +
                    "    \"body\": \"Golani fighter.\"\n" +
                    "  }\n" +
                    "}.";

            // Create the OpenAI request
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            Map<String, Object> requestPayload = Map.of(
                    "model", "gpt-4o",
                    "messages", List.of(
                            Map.of("role", "system", "content", "You are a CV writer expert."),
                            Map.of("role", "user", "content", prompt)
                    ),
                    "max_tokens", 1000,
                    "temperature", 0.7
            );

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestPayload, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(
                    "https://api.openai.com/v1/chat/completions",
                    entity,
                    String.class
            );

            // Return the full OpenAI response body as-is
            return response.getBody();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    public JsonObject createJson(JsonObject userInput) throws IOException, InterruptedException {
//
//        String apiKey = System.getenv("OPENAI_KEY");
//        Gson gson = new Gson();
//
//        String prompt = "You are an expert CV writer. Transform the following JSON into a detailed CV format. " +
//                "Here is the input: " + gson.toJson(userInput) +
//                " The output should follow this structure: " +
//                "{\n" +
//                "  \"personalDetails\": {\n" +
//                "    \"name\": \"John Doe\",\n" +
//                "    \"email\": \"johndoe@example.com\",\n" +
//                "    \"phone\": \"123-456-7890\",\n" +
//                "    \"linkedIn\": \"https://www.linkedin.com/in/johndoe\",\n" +
//                "    \"gitHub\": \"https://github.com/johndoe\",\n" +
//                "    \"summary\": \"Experienced software engineer with a passion for building innovative solutions.\"\n" +
//                "  },\n" +
//                "  \"jobs\": [\n" +
//                "    {\n" +
//                "      \"title\": \"Senior Software Engineer\",\n" +
//                "      \"company\": \"Acme Corporation\",\n" +
//                "      \"period\": \"Jan 2020 - Present\",\n" +
//                "      \"responsibilities\": [\n" +
//                "        \"Led the development of a new feature\",\n" +
//                "        \"Optimized performance of critical components\",\n" +
//                "        \"Mentored junior team members\"\n" +
//                "      ]\n" +
//                "    }\n" +
//                "  ],\n" +
//                "  \"educations\": [\n" +
//                "    {\n" +
//                "      \"degree\": \"Master of Science in Computer Science\",\n" +
//                "      \"institution\": \"Stanford University\",\n" +
//                "      \"year\": \"2018\",\n" +
//                "      \"achievements\": [\n" +
//                "        \"Published research paper in top-tier conference\",\n" +
//                "        \"Received academic scholarship\"\n" +
//                "      ]\n" +
//                "    }\n" +
//                "  ],\n" +
//                "  \"Skills\": [\"SHout\"],\n" +
//                "  \"preferredCvTemplate\": \"1\"\n" +
//                "}";
//
//        // Construct the OpenAI API request body
//        JsonObject requestBody = new JsonObject();
//        requestBody.addProperty("model", "gpt-4");
//        requestBody.add("messages", JsonParser.parseString(
//                "[{\"role\": \"user\", \"content\": \"" + prompt + "\"}]"
//        ));
//
//        // Create the HTTP request
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
//                .header("Content-Type", "application/json")
//                .header("Authorization", "Bearer " + apiKey)
//                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
//                .build();
//
//        // Send the request
//        HttpClient client = HttpClient.newHttpClient();
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//        // Parse and return the response
//        System.out.println("JsonParser.parseString(response.body()).getAsJsonObject(); " + JsonParser.parseString(response.body()).getAsJsonObject());
//        return JsonParser.parseString(response.body()).getAsJsonObject();
//    }

    /**
     * Sends a combined question to OpenAI that asks for both image selection and CV details.
     *
     * @param restTemplate RestTemplate for sending the request
     * @param prompt The combined prompt including image selection and CV details
     * @return The response content from OpenAI
     */
    private String askQuestion(RestTemplate restTemplate, String prompt) {
        ChatGPTRequest request = new ChatGPTRequest("gpt-4", prompt);
        ChatGptResponse response = restTemplate.postForObject(
                OPENAI_URL,
                Map.of(
                        "model", request.getModel(),
                        "messages", request.getMessages()
                ),
                ChatGptResponse.class
        );

        return response.getChoices().get(0).getMessage().getContent();
    }

    /**
     * Create the combined prompt asking for both image selection and CV details.
     *
     * @return Combined question prompt
     */
    public String createCombinedPrompt() {
        // Image selection choices in Base64
        String[] images = {
                "data:image/png;base64," + encodeImageToBase64("src/main/resources/images/1.png"),
                "data:image/png;base64," + encodeImageToBase64("src/main/resources/images/2.png"),
                "data:image/png;base64," + encodeImageToBase64("src/main/resources/images/3.png")
        };

        StringBuilder imageMessage = new StringBuilder("Please choose one of the following images by typing 1, 2, or 3:\n");
        for (int i = 0; i < images.length; i++) {
            imageMessage.append((i + 1)).append(". [Image ").append(i + 1).append("]").append("\n");
        }

        // CV questions prompt
        String cvQuestions = "\nTo help build your CV, please provide the following details:\n" +
                "1) Personal Details\n" +
                "What is your full name?\n" +
                "What is your email address?\n" +
                "What is your phone number?\n" +
                "What is the link to your LinkedIn profile? (Optional)\n" +
                "What is the link to your GitHub profile? (Optional)\n" +
                "What role are you looking for?\n" +
                "2) Job Experience\n" +
                "Do you have any job experiences you'd like to add to your CV? If yes, please provide the following details:\n" +
                "What was your job title?\n" +
                "What company did you work for?\n" +
                "What was the period of your employment? (e.g., Jan 2020 - Present)\n" +
                "What were your key responsibilities? (Please list them separated by commas)\n" +
                "Would you like to add another job experience? (Repeat as needed)\n" +
                "3) Education\n" +
                "Do you have any educational qualifications you'd like to add to your CV? If yes, please provide the following details:\n" +
                "What degree did you earn?\n" +
                "Where did you study (institution name)?\n" +
                "What was the period of your studies? (e.g., June 2020 - July 2024)\n" +
                "What were your achievements during your time at this institution? (Please list them separated by commas)\n" +
                "Would you like to add another educational qualification? (Repeat as needed)\n" +
                "4) Skills\n" +
                "What skills do you have? (Please list them separated by commas)\n" +
                "5) Preferred CV Template\n" +
                "Which CV template would you like to use? (You can provide the name of a template or describe your preferences, e.g., modern, simple, creative)";

        // Combined prompt with both image selection and CV details questions
        return imageMessage.toString() + cvQuestions;
    }

    /**
     * Parses the response from OpenAI to extract user inputs.
     *
     * @param responseContent The content from OpenAI response
     * @return A map of user inputs
     */
    private Map<String, String> parseResponse(String responseContent) {
        Map<String, String> userInputs = new HashMap<>();

        // Assuming the response content includes both the image selection and CV details
        // For example, user inputs may be given as:
        // "Image: 1, CV Details: {name: John Doe, email: john@example.com, ...}"
        String imageChoice = responseContent.split("Image: ")[1].split(",")[0].trim();
        String cvDetails = responseContent.split("CV Details: ")[1];

        // Save the image choice and CV details into the map
        userInputs.put("selectedImage", imageChoice);
        userInputs.put("userResponse", cvDetails);

        return userInputs;
    }

    /**
     * Encodes an image file to a Base64 string.
     *
     * @param imagePath Path to the image file
     * @return Base64-encoded string of the image
     */
    private String encodeImageToBase64(String imagePath) {
        try {
            byte[] fileContent = Files.readAllBytes(Paths.get(imagePath));
            return Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read or encode image", e);
        }
    }



    }