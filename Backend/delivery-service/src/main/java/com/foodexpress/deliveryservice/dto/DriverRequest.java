package com.foodexpress.deliveryservice.dto;

import jakarta.validation.constraints.NotNull;

public record DriverRequest(@NotNull Long userId, String vehicleType) {
}
