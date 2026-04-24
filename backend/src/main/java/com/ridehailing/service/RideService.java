package com.ridehailing.service;

import com.ridehailing.domain.Ride;
import com.ridehailing.persistence.RideEntity;
import com.ridehailing.persistence.RideRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

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
     * 
     * @return el ID del viaje creado.
     */
    @Transactional
    public String createRide() {
        RideEntity entity = new RideEntity();
        entity.setStateName("REQUESTING");
        RideEntity saved = rideRepository.save(entity);
        log.info("New ride created with ID: {}", saved.getId());
        return saved.getId();
    }

    /**
     * Ejecuta una acción en el viaje y actualiza su estado.
     * 
     * @param rideId ID del viaje.
     * @param action Acción a ejecutar.
     * @return El nombre del nuevo estado.
     */
    @Transactional
    public String performAction(@NonNull String rideId, @NonNull String action) {
        RideEntity entity = findRideOrThrow(rideId);
        Ride ride = reconstructFromEntity(entity);
        dispatchAction(ride, action);
        return persistNewState(entity, ride);
    }

    /**
     * Obtiene el estado actual de un viaje.
     * 
     * @param rideId ID del viaje.
     * @return El nombre del estado.
     */
    @Transactional(readOnly = true)
    public String getState(@NonNull String rideId) {
        return findRideOrThrow(rideId).getStateName();
    }

    private RideEntity findRideOrThrow(String rideId) {
        return rideRepository.findById(rideId).orElseThrow(() -> new RideNotFoundException(rideId));
    }

    private Ride reconstructFromEntity(RideEntity entity) {
        return new Ride(RideStateMapper.fromStateName(entity.getStateName()), entity.getStateName(),
                Collections.singletonList(entity.getStateName()));
    }

    private void dispatchAction(Ride ride, String action) {
        switch (action) {
            case "assignDriver" -> ride.assignDriver();
            case "driverArrives" -> ride.driverArrives();
            case "startTrip" -> ride.startTrip();
            case "completeTrip" -> ride.completeTrip();
            case "cancel" -> ride.cancel();
            default -> throw new IllegalArgumentException("Unknown action: " + action);
        }
    }

    private String persistNewState(RideEntity entity, Ride ride) {
        String newStateName = ride.getStateName();
        entity.setStateName(newStateName);
        rideRepository.save(entity);
        log.info("Ride {} transitioned to {}", entity.getId(), newStateName);
        return newStateName;
    }
}
