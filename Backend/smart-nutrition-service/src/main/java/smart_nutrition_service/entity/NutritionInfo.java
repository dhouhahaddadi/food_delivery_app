package smart_nutrition_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "nutrition_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NutritionInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long dishId; // Référence logique vers le plat (Restaurant Service)

    private String servingSize;
    private Double caloriesKcal;
    private Double proteinGrams;
    private Double carbohydrateGrams;
    private Double fatGrams;
    private Double fiberGrams;
    private Double sodiumMg;
}