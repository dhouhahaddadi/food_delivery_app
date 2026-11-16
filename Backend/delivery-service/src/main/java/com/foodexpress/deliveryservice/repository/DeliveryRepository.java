package com.foodexpress.deliveryservice.repository;

import com.foodexpress.deliveryservice.entity.Delivery;
import com.foodexpress.deliveryservice.entity.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    boolean existsByOrderId(Long orderId);
    List<Delivery> findByStatus(DeliveryStatus status);
}
