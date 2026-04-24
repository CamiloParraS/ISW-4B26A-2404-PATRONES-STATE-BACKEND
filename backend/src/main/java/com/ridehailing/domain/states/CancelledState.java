package com.ridehailing.domain.states;

import com.ridehailing.domain.Ride;
import com.ridehailing.domain.RideState;

public class CancelledState implements RideState {

    @Override
    public void assignDriver(Ride ride) {
        throw new IllegalStateException("El Viaje ya fue cancelado");
    }

    @Override
    public void driverArrives(Ride ride) {
        throw new IllegalStateException("El Viaje ya fue cancelado");
    }

    @Override
    public void startTrip(Ride ride) {
        throw new IllegalStateException("El Viaje ya fue cancelado");
    }

    @Override
    public void completeTrip(Ride ride) {
        throw new IllegalStateException("El Viaje ya fue cancelado");
    }

    @Override
    public void cancel(Ride ride) {
        throw new IllegalStateException("Este Viaje ya fue cancelado");
    }
}
