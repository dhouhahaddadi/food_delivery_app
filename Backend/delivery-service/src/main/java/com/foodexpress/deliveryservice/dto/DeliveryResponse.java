package com.foodexpress.deliveryservice.dto;

import com.foodexpress.deliveryservice.entity.Delivery;
import com.foodexpress.deliveryservice.entity.DeliveryStatus;

import java.time.LocalDateTime;

public record DeliveryResponse(
        Long id, Long orderId, Long driverId, DeliveryStatus status, String pickupAddress,
        String deliveryAddress, Integer estimatedMinutes, LocalDateTime createdAt,
        LocalDateTime assignedAt, LocalDateTime deliveredAt) {

    public static DeliveryResponse from(Delivery d) {
        return new DeliveryResponse(d.getId(), d.getOrderId(),
                d.getDriver() == null ? null : d.getDriver().getId(), d.getStatus(),
                d.getPickupAddress(), d.getDeliveryAddress(), d.getEstimatedMinutes(),
                d.getCreatedAt(), d.getAssignedAt(), d.getDeliveredAt());
    }
}
