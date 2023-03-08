package com.github.springbotplatform.core;

import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@Configuration
public class BotConfig {

    public String testString;
    public HashMap<String, Object> callMap = new HashMap();
    private HashMap<String, String> menuMap = new HashMap();

    public void putCallElement(String menuName, BotEventActionInterface inObject) {
        callMap.put(menuName, inObject);
    }

    public HashMap<String, String> getMenuMap() {
        return menuMap;
    }

    public void putInMenu(String menuName, String menuDescription) {
        menuMap.put("/" + menuName, new String(menuDescription.getBytes(), StandardCharsets.UTF_8));
    }

}

