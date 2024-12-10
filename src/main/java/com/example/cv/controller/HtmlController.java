package com.example.cv.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/file")
public class HtmlController {

    @GetMapping("/home")
    public String home(Model model) {
        // ... Your existing code for "home" page ...
        return "home"; // This will render 'home.html'
    }

//    @GetMapping("/test")
//    public String test(Model model) {
//        // ... Your existing code for "test" page ...
//        return "test"; // This will render 'test.html'
//    }
}
