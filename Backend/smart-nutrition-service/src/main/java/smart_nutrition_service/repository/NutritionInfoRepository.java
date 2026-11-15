package smart_nutrition_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import smart_nutrition_service.entity.NutritionInfo;

import java.util.Optional;

@Repository
public interface NutritionInfoRepository extends JpaRepository<NutritionInfo, Long> {
    // Permet de retrouver les infos nutritionnelles d'un plat via son ID logique
    Optional<NutritionInfo> findByDishId(Long dishId);
}