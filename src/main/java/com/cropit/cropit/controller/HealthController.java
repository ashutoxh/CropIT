package com.cropit.cropit.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class HealthController {

    @GetMapping("/health")
    public String checkHealth(){
        return "I'm alive!";
    }
}
