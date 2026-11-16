# User Service

Microservice **User** de FoodExpress : profils, adresses et préférences des utilisateurs.
Spring Boot 3 · Java 17 · PostgreSQL (`user_db`) · port 8081.

## Lancer

```bash
mvn spring-boot:run                                   # base H2 en mémoire
docker compose up -d                                  # PostgreSQL local
mvn spring-boot:run -Dspring-boot.run.profiles=postgres
```

## Endpoints (état actuel)

| Méthode | URI | Rôle | Codes |
|---|---|---|---|
| POST | /users | Créer un profil | 201, 400, 409 |
| GET | /users/{id} | Consulter un profil | 200, 404 |
| GET | /users | Lister les profils | 200 |
| DELETE | /users/{id} | Désactiver un compte | 204, 404 |

Adresses, préférences, sécurité Keycloak, Eureka et Config Server restent à ajouter.

## Modèle de données

`user_profile` (1) — (0..*) `address` ; `user_profile` (1) — (0..1) `preference`.
Diagrammes : voir `Documentation/Diagrams`.
