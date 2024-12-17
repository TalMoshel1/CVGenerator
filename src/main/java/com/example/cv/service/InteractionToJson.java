package com.example.cv.service;

import org.springframework.beans.factory.annotation.Value;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class InteractionToJson {

    private static final String OPENAI_URL = "https://api.openai.com/v1/chat/completions";

    @Value("${open.ai.key}")
    private String openAiKey;

    public String processUserJson(Map<String, Object> userData) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String userInputJson = objectMapper.writeValueAsString(userData);

            String prompt = "You are a CV writer expert. Please take the information you got from the user: " +
                    userInputJson +
                    ", and then translate it to the structure of this JSON object (apply the information instead of the values).Note: you might see github links both in projects and outside of project as github profile, differentiate between them. Note: add yourself information to the educations and jobs list until there are 3 of them in each list (the latest job need to be in the top and the oldest in the bottom) and add  more meat to the body of the project, but dont made up information for other peoperties in the JSON structure, and return the result of this long JSON structure with the new information: {\n" +
                    "  \"personalDetails\": {\n" +
                    "    \"name\": \"\",\n" +
                    "    \"email\": \"\",\n" +
                    "    \"phone\": \"\",\n" +
                    "    \"linkedIn\": \"\",\n" +
                    "    \"gitHub\": \"\",\n" +
                    "    \"summary\": \"\"\n" +
                    "  },\n" +
                    "  \"jobs\": [\n" +
                    "    {\n" +
                    "      \"title\": \"\",\n" +
                    "      \"company\": \"\",\n" +
                    "      \"period\": \"\",\n" +
                    "      \"responsibilities\": [\n" +
                    "        \"\",\n" +
                    "        \"\",\n" +
                    "        \"\"\n" +
                    "      ]\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"title\": \"\",\n" +
                    "      \"company\": \"\",\n" +
                    "      \"period\": \"\",\n" +
                    "      \"responsibilities\": [\n" +
                    "        \"\",\n" +
                    "        \"\"\n" +
                    "      ]\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"educations\": [\n" +
                    "    {\n" +
                    "      \"degree\": \"\",\n" +
                    "      \"institution\": \"\",\n" +
                    "      \"year\": \"\",\n" +
                    "      \"achievements\": [\n" +
                    "        \"\",\n" +
                    "        \"\"\n" +
                    "      ]\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"degree\": \"\",\n" +
                    "      \"institution\": \"\",\n" +
                    "      \"year\": \"\",\n" +
                    "      \"achievements\": [\n" +
                    "        \"\" \n"+
                    "      ]\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"Skills\": [\"\"],\n" +
                    "  \"preferredCvTemplate\": \"\",\n" +
                    "  \"projects\": [\n" +
                    "    {\n" +
                    "      \"role\": \"\",\n" +
                    "      \"project\": \"\",\n" +
                    "      \"technologies\": [\"\", \"\", \"\", \"\"],\n" +
                    "      \"body\": \"\",\n" +
                    "      \"urls\": {\n" +
                    "        \"githubRepository\": [\"\", \"\"],\n" +
                    "        \"live\": \"\"\n" +
                    "      }\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"army\": {\n" +
                    "    \"period\": \"\",\n" +
                    "    \"body\": \"\"\n" +
                    "  }\n" +
                    "}";

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(openAiKey);

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

            // Send request to OpenAI API
            ResponseEntity<String> response = restTemplate.postForEntity(
                    OPENAI_URL,
                    entity,
                    String.class
            );
            return response.getBody();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
