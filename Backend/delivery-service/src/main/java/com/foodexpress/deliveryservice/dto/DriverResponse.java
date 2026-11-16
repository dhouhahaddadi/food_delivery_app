package com.foodexpress.deliveryservice.dto;

import com.foodexpress.deliveryservice.entity.Driver;

import java.time.LocalDateTime;

public record DriverResponse(
        Long id, Long userId, String vehicleType, boolean available,
        Double latitude, Double longitude, LocalDateTime locationUpdatedAt) {

    public static DriverResponse from(Driver d) {
        return new DriverResponse(d.getId(), d.getUserId(), d.getVehicleType(), d.isAvailable(),
                d.getLatitude(), d.getLongitude(), d.getLocationUpdatedAt());
    }
}
