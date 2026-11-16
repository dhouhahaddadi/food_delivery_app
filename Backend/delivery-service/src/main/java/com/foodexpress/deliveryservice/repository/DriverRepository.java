package com.foodexpress.deliveryservice.repository;

import com.foodexpress.deliveryservice.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver, Long> {
    boolean existsByUserId(Long userId);
}
