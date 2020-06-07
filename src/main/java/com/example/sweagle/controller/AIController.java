package com.example.sweagle.controller;

import com.example.sweagle.service.AIService;
import com.example.sweagle.util.EnumUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/predict")
@Api(value="predict", description="Get estimation information")
public class AIController {

    private final AIService aiService;

    @Autowired
    public AIController(AIService aiService) {
        this.aiService = aiService;
    }

    @GetMapping
    @ApiOperation(value = "Predict number of messages per Day/Week",
            notes = "Use type=day to estimate the number of total messages per day," +
                    "Use type=week to estimate the number of total messages per week",
            response = Long.class,
            responseContainer = "List")
    public Long predictNumberOfMessages(@RequestParam("type") String type) {
        return aiService.predict(EnumUtils.convertPredictType(type));
    }
}
