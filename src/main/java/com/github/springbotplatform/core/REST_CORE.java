package com.github.springbotplatform.core;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/core")
public class REST_CORE {

    @GetMapping("/ping")
    public String getPing() {
        return "pong core";
    }
}
