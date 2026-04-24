package com.ridehailing.domain.states;

import com.ridehailing.domain.Ride;
import com.ridehailing.domain.RideState;

public class DriverAssignedState implements RideState {

    @Override
    public void assignDriver(Ride ride) {
        throw new IllegalStateException("Un conductor ya ha sido asignado a este viaje.");
    }

    @Override
    public void driverArrives(Ride ride) {
        ride.setState(new DriverArrivingState());
    }

    @Override
    public void startTrip(Ride ride) {
        throw new IllegalStateException(
                "No se puede iniciar el viaje antes de que el conductor llegue.");
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
