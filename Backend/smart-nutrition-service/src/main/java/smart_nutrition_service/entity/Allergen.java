package smart_nutrition_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "allergens")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Allergen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code; // Ex: GLUTEN, PEANUT[cite: 5]

    @Column(nullable = false)
    private String label; // Ex: Gluten, Arachides[cite: 5]
}