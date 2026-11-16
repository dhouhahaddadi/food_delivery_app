# Delivery Service

Microservice **Delivery** de FoodExpress : livreurs, livraisons, suivi de position.
Spring Boot 3 · Java 17 · PostgreSQL (`delivery_db`) · port 8082.

## Lancer

```bash
mvn spring-boot:run                                   # base H2 en mémoire
docker compose up -d                                  # PostgreSQL local (port 5433)
mvn spring-boot:run -Dspring-boot.run.profiles=postgres -Dspring-boot.run.arguments=--DB_PORT=5433
```

## Endpoints (état actuel)

| Méthode | URI | Rôle | Codes |
|---|---|---|---|
| POST | /deliveries | Créer une livraison | 201, 400, 409 |
| GET | /deliveries/{id} | Consulter | 200, 404 |
| GET | /deliveries?status= | Lister | 200 |
| PUT | /deliveries/{id}/assign/{driverId} | Affecter un livreur | 200, 404, 409 |
| PUT | /deliveries/{id}/status | Changer le statut | 200, 400, 404, 409 |
| GET | /deliveries/{id}/tracking | Suivre la livraison | 200, 404 |
| POST | /drivers | Créer un livreur | 201, 400, 409 |
| PUT | /drivers/{id}/location | Mettre à jour la position | 200, 400, 404 |

Cycle de vie : PENDING → ASSIGNED → PICKED_UP → ON_THE_WAY → DELIVERED (ou CANCELLED).

## À ajouter

RabbitMQ (consomme `OrderReadyForDelivery`, publie `DeliveryAssigned` et `DeliveryCompleted`),
OpenFeign vers User Service (adresse du client), sécurité Keycloak, Eureka et Config Server.
