package com.foodexpress.userservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "preference")
@Getter
@Setter
@NoArgsConstructor
public class Preference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserProfile user;

    @Enumerated(EnumType.STRING)
    @Column(name = "diet_type", length = 20)
    private DietType dietType = DietType.NONE;

    @Column(columnDefinition = "TEXT")
    private String allergies;

    @Column(name = "favorite_cuisines", columnDefinition = "TEXT")
    private String favoriteCuisines;

    @Column(name = "notifications_enabled", nullable = false)
    private boolean notificationsEnabled = true;
}
