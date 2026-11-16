package com.foodexpress.deliveryservice.dto;

import com.foodexpress.deliveryservice.entity.DeliveryStatus;
import jakarta.validation.constraints.NotNull;

public record StatusRequest(@NotNull DeliveryStatus status) {
}
