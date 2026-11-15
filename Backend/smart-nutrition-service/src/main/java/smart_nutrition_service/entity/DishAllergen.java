package smart_nutrition_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "dish_allergens")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishAllergen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long dishId;
    private Long allergenId;

    private String presenceLevel; // Ex: CONTAINS, MAY_CONTAIN, FREE_FROM[cite: 5]
    private String note;
}