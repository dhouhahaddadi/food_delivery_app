package smart_nutrition_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import smart_nutrition_service.entity.DishAllergen;

@Repository
public interface DishAllergenRepository extends JpaRepository<DishAllergen, Long> {
}