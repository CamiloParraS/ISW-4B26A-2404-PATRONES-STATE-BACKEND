package com.ridehailing.domain.states;

import com.ridehailing.domain.Ride;
import com.ridehailing.domain.RideState;

public class DriverArrivingState implements RideState {

    @Override
    public void assignDriver(Ride ride) {
        throw new IllegalStateException("Un conductor ya ha sido asignado a este viaje.");
    }

    @Override
    public void driverArrives(Ride ride) {
        throw new IllegalStateException("El conductor ya ha llegado.");
    }

    @Override
    public void startTrip(Ride ride) {
        ride.setState(new InTripState());
    }

    @Override
    public void completeTrip(Ride ride) {
        throw new IllegalStateException("No se puede completar el viaje antes de que comience.");
    }

    @Override
    public void cancel(Ride ride) {
        ride.setState(new CancelledState());
    }
}
