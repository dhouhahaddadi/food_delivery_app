# Smart Nutrition Service

Microservice de gestion des informations nutritionnelles et des allergènes pour la plateforme **FoodExpress**.

## Architecture

Ce microservice fait partie de l'architecture microservices FoodExpress :

- **Communication synchrone** : OpenFeign vers le Restaurant Service (`GET /api/dishes/{dishId}`)
- **Communication asynchrone** : RabbitMQ — écoute la queue `nutrition.dish-deleted` (exchange `foodexpress.events`)
- **Service Discovery** : Eureka Client
- **Configuration centralisée** : Spring Cloud Config Client

## Stack technique

| Composant      | Technologie                          |
|---------------|--------------------------------------|
| Framework     | Spring Boot 3.3.5, Java 17          |
| Base de données | H2 (dev) / PostgreSQL (prod)      |
| ORM           | Spring Data JPA / Hibernate          |
| Messaging     | RabbitMQ (Spring AMQP)               |
| Inter-service | OpenFeign                            |
| Discovery     | Eureka Client                        |
| Build         | Maven                                |

## Structure du projet

```
Backend/smart-nutrition-service/
├── src/main/java/smart_nutrition_service/
│   ├── SmartNutritionServiceApplication.java
│   ├── client/           # Feign client vers Restaurant Service
│   ├── config/           # Configuration RabbitMQ
│   ├── controller/       # REST API endpoints
│   ├── dto/              # Objets de transfert (Request/Response)
│   ├── entity/           # Entités JPA (NutritionInfo, Allergen, DishAllergen)
│   ├── event/            # Listeners RabbitMQ (DishDeletedEvent)
│   ├── exception/        # Gestion globale des erreurs
│   ├── repository/       # Repositories Spring Data
│   └── service/          # Logique métier
├── src/main/resources/
│   ├── application.properties
│   └── data.sql          # Jeu de données initial
└── pom.xml
```

## API REST

| Méthode | Endpoint                              | Description                              |
|---------|---------------------------------------|------------------------------------------|
| GET     | `/api/nutrition/ping`                 | Health check                             |
| GET     | `/api/nutrition/dishes/{dishId}`      | Fiche nutritionnelle d'un plat           |
| PUT     | `/api/nutrition/dishes/{dishId}`      | Créer / mettre à jour une fiche          |
| DELETE  | `/api/nutrition/dishes/{dishId}`      | Supprimer la fiche d'un plat             |
| GET     | `/api/nutrition/dishes/{dishId}/allergens` | Allergènes d'un plat              |
| GET     | `/api/nutrition/allergens`            | Liste de tous les allergènes             |
| POST    | `/api/nutrition/allergens`            | Ajouter un allergène au catalogue        |

## Lancer le service

```bash
cd Backend/smart-nutrition-service
./mvnw spring-boot:run
```

Le service démarre sur le port **8087** avec une base H2 en mémoire et des données de test pré-chargées.

- Console H2 : http://localhost:8087/h2-console (JDBC URL: `jdbc:h2:mem:nutrition_db`)
- Actuator : http://localhost:8087/actuator
