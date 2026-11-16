package com.foodexpress.deliveryservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DeliveryRequest(
        @NotNull Long orderId,
        @NotBlank String pickupAddress,
        @NotBlank String deliveryAddress,
        Integer estimatedMinutes) {
}
