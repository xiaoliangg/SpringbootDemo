package com.cdtelecom.util;

import com.google.gson.Gson;

public class GsonUtil {

    public static Object getJSONObject(String jsonString, Class<?> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString,clazz);
    }

    public static String getJSONString(Object object){
        Gson gson = new Gson();
        return gson.toJson(object);
    }
}
