package com.ridehailing.domain.states;

import com.ridehailing.domain.Ride;

/**
 * El conductor está en camino al punto de recogida del pasajero.
 *
 * <p>Transiciones válidas desde este estado:</p>
 * <ul>
 *   <li>{@link #startTrip(Ride)} → {@link InTripState}</li>
 *   <li>{@link #cancel(Ride)} → {@link CancelledState}</li>
 * </ul>
 *
 * <p>Todas las demás acciones lanzan {@link IllegalStateException}.</p>
 */
public class DriverArrivingState extends AbstractRideState {

    @Override
    protected String getNombreEstado() {
        return "CONDUCTOR EN CAMINO";
    }

    @Override
    public void startTrip(Ride ride) {
        ride.setState(new InTripState());
    }

    @Override
    public void cancel(Ride ride) {
        ride.setState(new CancelledState());
    }
}
