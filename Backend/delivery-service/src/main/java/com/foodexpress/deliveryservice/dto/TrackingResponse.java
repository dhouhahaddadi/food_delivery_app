package com.foodexpress.deliveryservice.dto;

import com.foodexpress.deliveryservice.entity.DeliveryStatus;

import java.time.LocalDateTime;

public record TrackingResponse(
        Long deliveryId, DeliveryStatus status, Long driverId,
        Double latitude, Double longitude, LocalDateTime locationUpdatedAt) {
}
