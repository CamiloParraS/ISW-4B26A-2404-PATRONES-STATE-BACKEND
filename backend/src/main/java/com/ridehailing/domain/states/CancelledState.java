package com.ridehailing.domain.states;

/**
 * Estado terminal: el viaje fue cancelado por el pasajero antes
 * de iniciar el trayecto.
 *
 * <p>No hay transiciones válidas desde este estado (es terminal).
 * Cualquier acción lanza {@link IllegalStateException}.</p>
 */
public class CancelledState extends AbstractRideState {

    @Override
    protected String getNombreEstado() {
        return "CANCELADO";
    }
}
