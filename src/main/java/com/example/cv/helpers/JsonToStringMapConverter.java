package com.example.cv.helpers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Map;


import java.util.HashMap;
import java.util.Map;

public class JsonToStringMapConverter {

    public static Map<String, Object> convertJsonToMap(JsonObject jsonObject) {
        Gson gson = new Gson();

        String jsonString = gson.toJson(jsonObject);

        Type type = new TypeToken<Map<String, Object>>() {}.getType();
        return gson.fromJson(jsonString, type);  // This will return the Map
    }
}
