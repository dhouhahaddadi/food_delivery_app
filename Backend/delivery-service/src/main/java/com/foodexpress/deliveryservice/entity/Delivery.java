package com.foodexpress.deliveryservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "delivery")
@Getter
@Setter
@NoArgsConstructor
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Référence logique vers la commande (Order & Payment), sans clé étrangère. */
    @Column(name = "order_id", nullable = false, unique = true)
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private DeliveryStatus status = DeliveryStatus.PENDING;

    @Column(name = "pickup_address", nullable = false, length = 255)
    private String pickupAddress;

    @Column(name = "delivery_address", nullable = false, length = 255)
    private String deliveryAddress;

    @Column(name = "estimated_minutes")
    private Integer estimatedMinutes;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "assigned_at")
    private LocalDateTime assignedAt;

    @Column(name = "delivered_at")
    private LocalDateTime deliveredAt;

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
