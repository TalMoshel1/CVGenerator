//package com.example.cv.images;
//
//
//import com.example.cv.openAI.ChatGPTRequest;
//import com.example.cv.openAI.ChatGptResponse;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.Base64;
//import java.util.Map;
//
//@Service
//public class ImageService {
//
//    private static final String OPENAI_URL = "https://api.openai.com/v1/chat/completions";
//    private static final String OPENAI_KEY = "your_openai_api_key";
//
//    /**
//     * Sends image options to the user and processes their choice.
//     *
//     * @param restTemplate RestTemplate to send the request
//     * @return Base64 string of the selected image
//     */
//    public String askImageChoice(RestTemplate restTemplate) {
//        // Example: Send images to the user as part of the message
//        String[] images = {
//                "data:image/png;base64," + encodeImageToBase64("/path/to/image1.png"),
//                "data:image/png;base64," + encodeImageToBase64("/path/to/image2.png"),
//                "data:image/png;base64," + encodeImageToBase64("/path/to/image3.png")
//        };
//
//        // Build the message to send to the user
//        StringBuilder imageMessage = new StringBuilder("Please choose one of the following images by typing 1, 2, or 3:\n");
//        for (int i = 0; i < images.length; i++) {
//            imageMessage.append((i + 1)).append(". [Image ").append(i + 1).append("]").append("\n");
//        }
//
//        // Send message and get the user's response
//        ChatGPTRequest request = new ChatGPTRequest("gpt-4", imageMessage.toString());
//        ChatGptResponse response = restTemplate.postForObject(
//                OPENAI_URL,
//                Map.of(
//                        "model", request.getModel(),
//                        "messages", request.getMessages()
//                ),
//                ChatGptResponse.class
//        );
//
//        // Extract the user's choice
//        String userChoice = response.getChoices().get(0).getMessage().getContent();
//        int choiceIndex = Integer.parseInt(userChoice.trim()) - 1;
//
//        // Return the selected image
//        return images[choiceIndex];
//    }
//
//    /**
//     * Encodes an image file to a Base64 string.
//     *
//     * @param imagePath Path to the image file
//     * @return Base64-encoded string of the image
//     */
//    private String encodeImageToBase64(String imagePath) {
//        try {
//            byte[] fileContent = Files.readAllBytes(Paths.get(imagePath));
//            return Base64.getEncoder().encodeToString(fileContent);
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to read or encode image", e);
//        }
//    }
//}
