package com.foodexpress.deliveryservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "driver")
@Getter
@Setter
@NoArgsConstructor
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Référence logique vers l'utilisateur (User Service), sans clé étrangère. */
    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "vehicle_type", length = 30)
    private String vehicleType;

    @Column(nullable = false)
    private boolean available = true;

    private Double latitude;

    private Double longitude;

    @Column(name = "location_updated_at")
    private LocalDateTime locationUpdatedAt;
}
