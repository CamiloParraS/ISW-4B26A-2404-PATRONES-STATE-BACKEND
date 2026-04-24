package com.ridehailing.domain.states;

/**
 * Estado terminal: el viaje finalizó exitosamente y el pasajero
 * llegó a su destino.
 *
 * <p>No hay transiciones válidas desde este estado (es terminal).
 * Cualquier acción lanza {@link IllegalStateException}.</p>
 */
public class CompletedState extends AbstractRideState {

    @Override
    protected String getNombreEstado() {
        return "COMPLETADO";
    }
}
