# FoodExpress

Plateforme web de commande de repas et de livraison pour un restaurant, construite sur une architecture microservices.

Projet AWD, ESPRIT 2026-2027, classe 4DAS-I7, groupe A COMPLETER.

## Sujet

FoodExpress permet aux clients d'un restaurant de commander en ligne et d'être livrés par les propres livreurs du restaurant, sans dépendre d'une plateforme tierce. Le client consulte le menu, commande, paie (paiement simulé ou en mode test) et suit sa livraison. Le restaurant gère son catalogue, ses commandes et ses livreurs.

Valeur ajoutée :
- recommandations personnalisées de plats
- offres anti-gaspillage limitées dans le temps
- valeurs nutritionnelles et allergènes par plat

Règle métier : un seul restaurant, qui livre avec ses propres livreurs (ni flotte centrale, ni plateforme de livraison tierce).

## Équipe

| Membre | Microservice | GitHub |
|--------|--------------|--------|
| Yasmine Mansour | User Service | |
| Dhouha Haddadi | Restaurant Service | |
| Mariem Manai | Order & Payment Service | |
| Ayoub Ben Amor | Delivery Service | |
|  | Recommendation Service | |
| Yassine Gabsi | Food Waste Reduction ||
|Isra Brini| Smart Nutrition | |


## Acteurs

- **Client** : profil, adresses, recherche, panier, commande, paiement, suivi, recommandations
- **Restaurant** : menus, plats, prix, disponibilité, traitement des commandes, gestion de ses livreurs
- **Livreur du restaurant** : missions, mise à jour du statut, confirmation de remise
- **Administrateur** : comptes et rôles, supervision, statistiques, réclamations

## Architecture

![Architecture globale](Documentation/Diagrams/architecture.png)

Toute la plateforme est conteneurisée avec Docker Compose. Le navigateur (React) passe par une API Gateway qui valide les jetons JWT émis par Keycloak, puis route vers les microservices. Chaque service possède sa propre base de données (aucune base partagée) et s'enregistre dans Eureka.

### Microservices

| N° | Microservice | Technologie | Base | Responsabilité |
|----|--------------|-------------|------|----------------|
| 1 | User Service | Spring Boot | PostgreSQL | Profils, adresses, rôles applicatifs |
| 2 | Restaurant Service | Spring Boot | PostgreSQL | Restaurant, menus, catégories, plats, prix, disponibilité |
| 3 | Order & Payment Service | Spring Boot | PostgreSQL | Paniers, commandes, paiements |
| 4 | Delivery Service | Spring Boot | PostgreSQL | Livreurs du restaurant, affectation, suivi |
| 5 | Recommendation Service | FastAPI, Scikit-learn | MongoDB | Suggestions personnalisées |
| 6 | Food Waste Reduction | Spring Boot | PostgreSQL | Offres anti-gaspillage |
| 7 | Smart Nutrition Service | Spring Boot | PostgreSQL | Valeurs nutritionnelles, allergènes, filtres |

### Infrastructure

| Composant | Rôle |
|-----------|------|
| API Gateway (Spring Cloud Gateway) | Point d'entrée unique, routage `/api`, validation des jetons |
| Eureka | Découverte des services |
| Config Server | Configuration centralisée |
| Keycloak | Authentification, JWT, rôles (CUSTOMER, RESTAURANT, DRIVER, ADMIN) |
| RabbitMQ | Événements asynchrones |
| Docker Compose | Conteneurisation de toute la plateforme |

### Communications

**Synchrone (REST / OpenFeign)** : Order & Payment vers Restaurant (existence, prix, disponibilité) et Food Waste ; Delivery vers User ; Food Waste et Smart Nutrition vers Restaurant. Si le service appelé est indisponible : timeout, nouvelle tentative limitée, erreur 503.

**Asynchrone (RabbitMQ)** :

| Événement | Émetteur | Consommateur(s) |
|-----------|----------|-----------------|
| OrderCreated | Order & Payment | Restaurant |
| PaymentConfirmed / PaymentFailed | Order & Payment | Order & Payment |
| OrderReadyForDelivery | Restaurant | Delivery |
| DeliveryAssigned | Delivery | Order & Payment |
| DeliveryCompleted | Delivery | Order & Payment |
| OrderItemPurchased | Order & Payment | Food Waste, Recommendation |

Format commun des événements : `eventId`, `eventType`, `occurredAt`, `aggregateId`, `payload`. Les consommateurs sont idempotents.

## Structure du dépôt

```
food_delivery_app/
├── Backend/
│   ├── user-service/
│   ├── restaurant-service/
│   ├── order-payment-service/
│   ├── delivery-service/
│   ├── recommendation-service/
│   ├── food-waste-service/
│   ├── smart-nutrition-service/
│   └── infrastructure/        # gateway, eureka, config-server, docker-compose
├── Frontend/                  # application React
├── Documentation/
│   ├── Presentation/          # présentation de la séance 4
│   └── Diagrams/              # architecture globale et diagrammes principaux
└── README.md
```

## Technologies

- Frontend : React
- Backend métier : Java, Spring Boot
- Service IA : Python, FastAPI, Scikit-learn
- Infrastructure : Spring Cloud Gateway, Eureka, Config Server
- Sécurité : Keycloak, Spring Security
- Communication : REST / OpenFeign, RabbitMQ
- Données : PostgreSQL, MongoDB
- Outils : Docker Compose, GitHub, Postman, Draw.io, Swagger

## Lancement

### Prérequis

- JDK 17, Maven (ou `mvnw`)
- Node.js et npm
- Docker et Docker Compose
- Python 3.10+ (pour Recommendation Service)

### Plateforme complète

```bash
cd Backend/infrastructure
docker compose up --build
```

### Un microservice seul (exemple : Restaurant Service)

```bash
cd Backend/restaurant-service
./mvnw spring-boot:run
```

Sous Windows PowerShell : `.\mvnw spring-boot:run`

Configurer la connexion PostgreSQL dans `src/main/resources/application.properties`. La documentation Swagger est disponible sur `/swagger-ui.html` du service.

### Frontend

```bash
cd Frontend
npm install
npm start
```

Chaque microservice contient aussi ses propres instructions de lancement.

## Organisation du travail

- Suivi des tâches : GitHub Projects (A COMPLETER : lien du tableau)
- Branches : `main` (stable), `develop` (intégration), une branche par microservice ou fonctionnalité (`restaurant_service`, `feature/...`)
- Pas de push direct sur `main` : Pull Request relue par un autre membre
- Commits réguliers et personnels, convention `feat:`, `fix:`, `docs:`, `chore:`

## Documentation

- Présentation de la séance 4 : `Documentation/Presentation/`
- Diagramme d'architecture globale et diagrammes principaux : `Documentation/Diagrams/`
- Dossier technique (Word ou LaTeX) : `Documentation/`
