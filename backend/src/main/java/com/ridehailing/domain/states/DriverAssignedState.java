package com.ridehailing.domain.states;

import com.ridehailing.domain.Ride;

/**
 * El conductor ha sido asignado al viaje y está por dirigirse
 * al punto de recogida.
 *
 * <p>Transiciones válidas desde este estado:</p>
 * <ul>
 *   <li>{@link #driverArrives(Ride)} → {@link DriverArrivingState}</li>
 *   <li>{@link #cancel(Ride)} → {@link CancelledState}</li>
 * </ul>
 *
 * <p>Todas las demás acciones lanzan {@link IllegalStateException}.</p>
 */
public class DriverAssignedState extends AbstractRideState {

    @Override
    protected String getNombreEstado() {
        return "CONDUCTOR ASIGNADO";
    }

    @Override
    public void driverArrives(Ride ride) {
        ride.setState(new DriverArrivingState());
    }

    @Override
    public void cancel(Ride ride) {
        ride.setState(new CancelledState());
    }
}
