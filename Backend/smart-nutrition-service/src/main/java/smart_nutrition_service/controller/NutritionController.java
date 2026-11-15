package smart_nutrition_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/nutrition")
public class NutritionController {

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("Smart Nutrition Service is up and running!");
    }

    @GetMapping("/dishes/{dishId}")
    public ResponseEntity<Object> getNutritionByDishId(@PathVariable Long dishId) {
        // Retourne 501 Not Implemented en attendant l'implémentation complète
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Not implemented yet");
    }

    @GetMapping("/allergens")
    public ResponseEntity<Object> getAllAllergens() {
        // Retourne 501 Not Implemented en attendant l'implémentation complète
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Not implemented yet");
    }
}