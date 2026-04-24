package com.ridehailing.domain.states;

import com.ridehailing.domain.Ride;

/**
 * El viaje está en curso: el pasajero abordó y el conductor se dirige
 * al destino.
 *
 * <p>Transiciones válidas desde este estado:</p>
 * <ul>
 *   <li>{@link #completeTrip(Ride)} → {@link CompletedState}</li>
 * </ul>
 *
 * <p>No se permite cancelar una vez el viaje ha iniciado.
 * Todas las demás acciones lanzan {@link IllegalStateException}.</p>
 */
public class InTripState extends AbstractRideState {

    @Override
    protected String getNombreEstado() {
        return "EN VIAJE";
    }

    @Override
    public void completeTrip(Ride ride) {
        ride.setState(new CompletedState());
    }
}
