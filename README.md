# order-payment-service

Microservice de gestion des commandes, des paniers et des paiements.
Responsable : Mariem MANAI.

## Responsabilités

- Gérer les commandes et les paniers (un panier est une commande au statut `CART`)
- Gérer le détail d'une commande (plats, quantités, prix)
- Gérer les paiements d'une commande

## Technologies

Spring Boot, Java 17, Maven, Spring Data JPA, MySQL, Eureka Client, OpenFeign.

## Entités

`Order`, `OrderItem`, `Payment` et les énumérations `OrderStatus` et `PaymentStatus`.
Le diagramme de classes est dans `Documentation/Diagrams/`.

## Lancement

1. Démarrer MySQL .
2. Lancer le service :

```bash
mvn spring-boot:run
```

Le service démarre sur le port `8083`. La base `order_payment_db` est créée automatiquement.

## API

| Méthode | URI | Description | Codes |
|---|---|---|---|
| POST | /orders | Créer un panier | 201, 400, 404 |
| POST | /orders/{id}/items | Ajouter un plat | 201, 400, 404 |
| DELETE | /orders/{id}/items/{itemId} | Retirer un plat | 204, 404 |
| GET | /orders/{id} | Consulter une commande | 200, 404 |
| GET | /orders?userId={id} | Commandes d'un utilisateur | 200 |
| PUT | /orders/{id}/confirm | Valider le panier | 200, 400, 404, 503 |
| PUT | /orders/{id}/cancel | Annuler la commande | 200, 404, 409 |
| POST | /payments | Payer une commande | 201, 400, 404, 409 |
| GET | /payments/{id} | Consulter un paiement | 200, 404 |

Événement publié : `OrderPaid`, après un paiement réussi.

## Interactions

- Synchrone (OpenFeign) : Restaurant Service (prix et disponibilité des plats), User Service (existence de l'utilisateur)
- Asynchrone (événement `OrderPaid`) : Delivery Service, Food Waste Service, Recommendation Service
