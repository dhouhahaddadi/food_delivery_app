package smart_nutrition_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/nutrition")
public class NutritionController {

    @GetMapping("/ping")
    public String ping() {
        return "Smart Nutrition Service is up and running!";
    }

    @GetMapping
    public String index() {
        return "Smart Nutrition Service is up and running!";
    }
}
