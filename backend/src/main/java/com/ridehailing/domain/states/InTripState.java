package com.ridehailing.domain.states;

import com.ridehailing.domain.Ride;
import com.ridehailing.domain.RideState;

public class InTripState implements RideState {

    @Override
    public void assignDriver(Ride ride) {
        throw new IllegalStateException(
                "El viaje ya ha comenzado, el conductor ya está en camino.");
    }

    @Override
    public void driverArrives(Ride ride) {
        throw new IllegalStateException(
                "El viaje ya ha comenzado, el conductor ya está en camino.");
    }

    @Override
    public void startTrip(Ride ride) {
        throw new IllegalStateException("El viaje ya ha comenzado.");
    }

    @Override
    public void completeTrip(Ride ride) {
        ride.setState(new CompletedState());
    }

    @Override
    public void cancel(Ride ride) {
        throw new IllegalStateException("Un viaje en Progreso no puede ser cancelado.");
    }
}
