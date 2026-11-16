package com.foodexpress.deliveryservice.service;

import com.foodexpress.deliveryservice.dto.*;
import com.foodexpress.deliveryservice.entity.Delivery;
import com.foodexpress.deliveryservice.entity.DeliveryStatus;
import com.foodexpress.deliveryservice.entity.Driver;
import com.foodexpress.deliveryservice.exception.ConflictException;
import com.foodexpress.deliveryservice.exception.NotFoundException;
import com.foodexpress.deliveryservice.repository.DeliveryRepository;
import com.foodexpress.deliveryservice.repository.DriverRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Transactional
public class DeliveryService {

    /** Transitions autorisées du cycle de vie d'une livraison. */
    private static final Map<DeliveryStatus, Set<DeliveryStatus>> TRANSITIONS = Map.of(
            DeliveryStatus.PENDING, Set.of(DeliveryStatus.ASSIGNED, DeliveryStatus.CANCELLED),
            DeliveryStatus.ASSIGNED, Set.of(DeliveryStatus.PICKED_UP, DeliveryStatus.CANCELLED),
            DeliveryStatus.PICKED_UP, Set.of(DeliveryStatus.ON_THE_WAY, DeliveryStatus.CANCELLED),
            DeliveryStatus.ON_THE_WAY, Set.of(DeliveryStatus.DELIVERED, DeliveryStatus.CANCELLED),
            DeliveryStatus.DELIVERED, Set.of(),
            DeliveryStatus.CANCELLED, Set.of());

    private final DeliveryRepository deliveries;
    private final DriverRepository drivers;

    public DeliveryService(DeliveryRepository deliveries, DriverRepository drivers) {
        this.deliveries = deliveries;
        this.drivers = drivers;
    }

    // ---- livraisons ----

    public DeliveryResponse create(DeliveryRequest r) {
        if (deliveries.existsByOrderId(r.orderId())) {
            throw new ConflictException("Une livraison existe déjà pour la commande " + r.orderId());
        }
        Delivery d = new Delivery();
        d.setOrderId(r.orderId());
        d.setPickupAddress(r.pickupAddress());
        d.setDeliveryAddress(r.deliveryAddress());
        d.setEstimatedMinutes(r.estimatedMinutes());
        return DeliveryResponse.from(deliveries.save(d));
    }

    @Transactional(readOnly = true)
    public DeliveryResponse get(Long id) {
        return DeliveryResponse.from(findDelivery(id));
    }

    @Transactional(readOnly = true)
    public List<DeliveryResponse> list(DeliveryStatus status) {
        List<Delivery> found = status == null ? deliveries.findAll() : deliveries.findByStatus(status);
        return found.stream().map(DeliveryResponse::from).toList();
    }

    public DeliveryResponse assign(Long deliveryId, Long driverId) {
        Delivery d = findDelivery(deliveryId);
        Driver driver = findDriver(driverId);
        if (d.getStatus() != DeliveryStatus.PENDING) {
            throw new ConflictException("La livraison n'est plus en attente : " + d.getStatus());
        }
        if (!driver.isAvailable()) {
            throw new ConflictException("Le livreur n'est pas disponible");
        }
        d.setDriver(driver);
        d.setStatus(DeliveryStatus.ASSIGNED);
        d.setAssignedAt(LocalDateTime.now());
        driver.setAvailable(false);
        return DeliveryResponse.from(d);
    }

    public DeliveryResponse updateStatus(Long id, DeliveryStatus next) {
        Delivery d = findDelivery(id);
        if (!TRANSITIONS.get(d.getStatus()).contains(next)) {
            throw new ConflictException("Transition interdite : " + d.getStatus() + " -> " + next);
        }
        d.setStatus(next);
        if (next == DeliveryStatus.DELIVERED) {
            d.setDeliveredAt(LocalDateTime.now());
        }
        if ((next == DeliveryStatus.DELIVERED || next == DeliveryStatus.CANCELLED) && d.getDriver() != null) {
            d.getDriver().setAvailable(true);
        }
        return DeliveryResponse.from(d);
    }

    @Transactional(readOnly = true)
    public TrackingResponse tracking(Long id) {
        Delivery d = findDelivery(id);
        Driver driver = d.getDriver();
        return new TrackingResponse(d.getId(), d.getStatus(),
                driver == null ? null : driver.getId(),
                driver == null ? null : driver.getLatitude(),
                driver == null ? null : driver.getLongitude(),
                driver == null ? null : driver.getLocationUpdatedAt());
    }

    // ---- livreurs ----

    public DriverResponse createDriver(DriverRequest r) {
        if (drivers.existsByUserId(r.userId())) {
            throw new ConflictException("Un livreur existe déjà pour l'utilisateur " + r.userId());
        }
        Driver d = new Driver();
        d.setUserId(r.userId());
        d.setVehicleType(r.vehicleType());
        return DriverResponse.from(drivers.save(d));
    }

    public DriverResponse updateLocation(Long driverId, LocationRequest r) {
        Driver d = findDriver(driverId);
        d.setLatitude(r.latitude());
        d.setLongitude(r.longitude());
        d.setLocationUpdatedAt(LocalDateTime.now());
        return DriverResponse.from(d);
    }

    private Delivery findDelivery(Long id) {
        return deliveries.findById(id).orElseThrow(() -> new NotFoundException("Livraison introuvable : " + id));
    }

    private Driver findDriver(Long id) {
        return drivers.findById(id).orElseThrow(() -> new NotFoundException("Livreur introuvable : " + id));
    }
}
