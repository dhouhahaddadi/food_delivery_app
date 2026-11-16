package com.foodexpress.deliveryservice.dto;

import jakarta.validation.constraints.NotNull;

public record LocationRequest(@NotNull Double latitude, @NotNull Double longitude) {
}
