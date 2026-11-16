package com.foodexpress.userservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "address")
@Getter
@Setter
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserProfile user;

    @Column(length = 50)
    private String label;

    @Column(nullable = false, length = 255)
    private String street;

    @Column(nullable = false, length = 100)
    private String city;

    @Column(name = "postal_code", length = 10)
    private String postalCode;

    private Double latitude;

    private Double longitude;

    @Column(name = "is_default", nullable = false)
    private boolean isDefault = false;

    public void markAsDefault() {
        this.isDefault = true;
    }
}
