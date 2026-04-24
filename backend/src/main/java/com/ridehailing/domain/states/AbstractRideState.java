package com.ridehailing.domain.states;

import com.ridehailing.domain.Ride;
import com.ridehailing.domain.RideState;

/**
 * Implementación base de {@link RideState} que lanza
 * {@link IllegalStateException} para cada acción por defecto.
 *
 * <p>Los estados concretos extienden esta clase y solo sobreescriben
 * las acciones que son válidas en su fase del ciclo de vida,
 * heredando el comportamiento de "denegar por defecto" para todo
 * lo demás. Esto elimina código repetitivo (principio DRY).</p>
 */
public abstract class AbstractRideState implements RideState {

    /**
     * Retorna un nombre legible del estado para usar en mensajes de error.
     *
     * @return nombre del estado en español (por ejemplo, "SOLICITANDO")
     */
    protected abstract String getNombreEstado();

    @Override
    public void requestRide(Ride ride) {
        throw new IllegalStateException(
                "No se puede solicitar un viaje en estado " + getNombreEstado() + ".");
    }

    @Override
    public void assignDriver(Ride ride) {
        throw new IllegalStateException(
                "No se puede asignar un conductor en estado " + getNombreEstado() + ".");
    }

    @Override
    public void driverArrives(Ride ride) {
        throw new IllegalStateException(
                "El conductor no puede llegar en estado " + getNombreEstado() + ".");
    }

    @Override
    public void startTrip(Ride ride) {
        throw new IllegalStateException(
                "No se puede iniciar el viaje en estado " + getNombreEstado() + ".");
    }

    @Override
    public void completeTrip(Ride ride) {
        throw new IllegalStateException(
                "No se puede completar el viaje en estado " + getNombreEstado() + ".");
    }

    @Override
    public void cancel(Ride ride) {
        throw new IllegalStateException(
                "No se puede cancelar el viaje en estado " + getNombreEstado() + ".");
    }
}
