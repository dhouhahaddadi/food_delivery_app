# order-payment-service

Microservice Commandes et Paiements (Spring Boot 3, Java 17, Maven).

## Lancer
```bash
mvn spring-boot:run
```
Le service démarre sur le port 8083 avec une base H2 en mémoire.
Pour PostgreSQL, voir `src/main/resources/application.properties`.

## Contenu
- Entités : `Order`, `OrderItem`, `Payment` (+ enums `OrderStatus`, `PaymentStatus`)
- Dépendances : Web, JPA, PostgreSQL, H2, Eureka Client, OpenFeign, Config Client, Lombok
