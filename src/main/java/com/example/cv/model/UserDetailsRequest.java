package com.example.cv.model;

import java.util.Map;

public class UserDetailsRequest {
    private String filename;
    private Map<String, String> userDetails;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Map<String, String> getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(Map<String, String> userDetails) {
        this.userDetails = userDetails;
    }
}
