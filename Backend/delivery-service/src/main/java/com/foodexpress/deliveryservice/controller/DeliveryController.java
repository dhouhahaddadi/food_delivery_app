package com.foodexpress.deliveryservice.controller;

import com.foodexpress.deliveryservice.dto.*;
import com.foodexpress.deliveryservice.entity.DeliveryStatus;
import com.foodexpress.deliveryservice.service.DeliveryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deliveries")
public class DeliveryController {

    private final DeliveryService service;

    public DeliveryController(DeliveryService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DeliveryResponse create(@Valid @RequestBody DeliveryRequest request) {
        return service.create(request);
    }

    @GetMapping("/{id}")
    public DeliveryResponse get(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping
    public List<DeliveryResponse> list(@RequestParam(required = false) DeliveryStatus status) {
        return service.list(status);
    }

    @PutMapping("/{id}/assign/{driverId}")
    public DeliveryResponse assign(@PathVariable Long id, @PathVariable Long driverId) {
        return service.assign(id, driverId);
    }

    @PutMapping("/{id}/status")
    public DeliveryResponse updateStatus(@PathVariable Long id, @Valid @RequestBody StatusRequest request) {
        return service.updateStatus(id, request.status());
    }

    @GetMapping("/{id}/tracking")
    public TrackingResponse tracking(@PathVariable Long id) {
        return service.tracking(id);
    }
}
