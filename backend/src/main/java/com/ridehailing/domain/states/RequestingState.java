package com.ridehailing.domain.states;

import com.ridehailing.domain.Ride;

/**
 * Estado inicial: el pasajero ha solicitado un viaje pero aún no se
 * ha asignado un conductor.
 *
 * <p>Transiciones válidas desde este estado:</p>
 * <ul>
 *   <li>{@link #assignDriver(Ride)} → {@link DriverAssignedState}</li>
 *   <li>{@link #cancel(Ride)} → {@link CancelledState}</li>
 * </ul>
 *
 * <p>Todas las demás acciones lanzan {@link IllegalStateException}.</p>
 */
public class RequestingState extends AbstractRideState {

    @Override
    protected String getNombreEstado() {
        return "SOLICITANDO";
    }

    @Override
    public void assignDriver(Ride ride) {
        ride.setState(new DriverAssignedState());
    }

    @Override
    public void cancel(Ride ride) {
        ride.setState(new CancelledState());
    }
}
