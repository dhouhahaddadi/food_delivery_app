package com.foodexpress.deliveryservice.controller;

import com.foodexpress.deliveryservice.dto.DriverRequest;
import com.foodexpress.deliveryservice.dto.DriverResponse;
import com.foodexpress.deliveryservice.dto.LocationRequest;
import com.foodexpress.deliveryservice.service.DeliveryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/drivers")
public class DriverController {

    private final DeliveryService service;

    public DriverController(DeliveryService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DriverResponse create(@Valid @RequestBody DriverRequest request) {
        return service.createDriver(request);
    }

    @PutMapping("/{id}/location")
    public DriverResponse updateLocation(@PathVariable Long id, @Valid @RequestBody LocationRequest request) {
        return service.updateLocation(id, request);
    }
}
