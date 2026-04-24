package com.ridehailing.domain.states;

import com.ridehailing.domain.Ride;
import com.ridehailing.domain.RideState;

public class CompletedState implements RideState {

    @Override
    public void assignDriver(Ride ride) {
        throw new IllegalStateException("Este viaje ya ha sido completado.");
    }

    @Override
    public void driverArrives(Ride ride) {
        throw new IllegalStateException("Este viaje ya ha sido completado.");
    }

    @Override
    public void startTrip(Ride ride) {
        throw new IllegalStateException("Este viaje ya ha sido completado.");
    }

    @Override
    public void completeTrip(Ride ride) {
        throw new IllegalStateException("Este viaje ya ha sido completado.");
    }

    @Override
    public void cancel(Ride ride) {
        throw new IllegalStateException("Un viaje completado no puede ser cancelado.");
    }
}
