package com.ridehailing.domain.states;

import com.ridehailing.domain.Ride;
import com.ridehailing.domain.RideState;

public class RequestingState implements RideState {

    @Override
    public void assignDriver(Ride ride) {
        ride.setState(new DriverAssignedState());
    }

    @Override
    public void driverArrives(Ride ride) {
        throw new IllegalStateException(
                "No se puede marcar la llegada del conductor antes de asignarlo.");
    }

    @Override
    public void startTrip(Ride ride) {
        throw new IllegalStateException(
                "No se puede iniciar el viaje antes de que el conductor llegue.");
    }

    @Override
    public void completeTrip(Ride ride) {
        throw new IllegalStateException("no se puede completar el viaje antes de que comience.");
    }

    @Override
    public void cancel(Ride ride) {
        ride.setState(new CancelledState());
    }
}
