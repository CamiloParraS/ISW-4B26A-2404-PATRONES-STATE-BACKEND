package com.ridehailing.service;

import com.ridehailing.domain.Ride;
import com.ridehailing.persistence.RideEntity;
import com.ridehailing.persistence.RideRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import org.springframework.lang.NonNull;

/**
 * Servicio para orquestar la lógica del ciclo de vida del viaje.
 */
@Service
public class RideService {

    private static final Logger log = LoggerFactory.getLogger(RideService.class);
    private final RideRepository rideRepository;

    public RideService(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    /**
     * Crea un nuevo viaje y lo persiste en la base de datos.
     * @return el ID del viaje creado.
     */
    @Transactional
    public String createRide() {
        RideEntity entity = new RideEntity();
        entity.setStateName("REQUESTING");
        RideEntity saved = rideRepository.save(entity);
        log.info("Nuevo viaje creado con ID: {}", saved.getId());
        return saved.getId();
    }

    /**
     * Ejecuta una acción en el viaje y actualiza su estado.
     * @param rideId ID del viaje.
     * @param action Acción a ejecutar.
     * @return El nombre del nuevo estado.
     */
    @Transactional
    public String performAction(@NonNull String rideId, @NonNull String action) {
        RideEntity entity = rideRepository.findById(rideId)
                .orElseThrow(() -> new RideNotFoundException(rideId));

        // Reconstruimos el objeto del dominio desde el estado guardado
        Ride ride = new Ride(
                RideStateMapper.fromStateName(entity.getStateName()),
                entity.getStateName(),
                Collections.singletonList(entity.getStateName()) // Historial simplificado para el servicio
        );

        log.info("Ejecutando acción '{}' en viaje {} (estado actual: {})", action, rideId, entity.getStateName());

        switch (action) {
            case "requestRide" -> ride.requestRide();
            case "assignDriver" -> ride.assignDriver();
            case "driverArrives" -> ride.driverArrives();
            case "startTrip" -> ride.startTrip();
            case "completeTrip" -> ride.completeTrip();
            case "cancel" -> ride.cancel();
            default -> throw new IllegalArgumentException("Acción desconocida: " + action);
        }

        String newStateName = ride.getStateName();
        entity.setStateName(newStateName);
        rideRepository.save(entity);
        
        log.info("Viaje {} cambió de estado a {}", rideId, newStateName);
        return newStateName;
    }

    /**
     * Obtiene el estado actual de un viaje.
     * @param rideId ID del viaje.
     * @return El nombre del estado.
     */
    @Transactional(readOnly = true)
    public String getState(@NonNull String rideId) {
        return rideRepository.findById(rideId)
                .map(RideEntity::getStateName)
                .orElseThrow(() -> new RideNotFoundException(rideId));
    }
}