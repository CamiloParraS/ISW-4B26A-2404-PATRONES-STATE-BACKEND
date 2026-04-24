package com.ridehailing.service;

import com.ridehailing.domain.Ride;
import com.ridehailing.persistence.RideEntity;
import com.ridehailing.persistence.RideRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RideService {

    private final RideRepository rideRepository;

    public RideService(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    @Transactional
    public String createRide() {
        RideEntity entity = new RideEntity();
        entity.setStateName("REQUESTING");
        RideEntity saved = rideRepository.save(entity);
        return saved.getId();
    }

    @Transactional
    public String performAction(String rideId, String action) {
        RideEntity entity = rideRepository.findById(rideId)
                .orElseThrow(() -> new RideNotFoundException(rideId));

        Ride ride = new Ride();
        ride.setState(RideStateMapper.fromStateName(entity.getStateName()));

        switch (action) {
            case "requestRide" -> ride.requestRide();
            case "assignDriver" -> ride.assignDriver();
            case "driverArrives" -> ride.driverArrives();
            case "startTrip" -> ride.startTrip();
            case "completeTrip" -> ride.completeTrip();
            case "cancel" -> ride.cancel();
            default -> throw new IllegalArgumentException("Unknown action: " + action);
        }

        String newStateName = ride.getStateName();
        entity.setStateName(newStateName);
        rideRepository.save(entity);
        return newStateName;
    }

    @Transactional(readOnly = true)
    public String getState(String rideId) {
        return rideRepository.findById(rideId)
                .map(RideEntity::getStateName)
                .orElseThrow(() -> new RideNotFoundException(rideId));
    }
}