package com.ridehailing.domain;

/**
 * Define el contrato para todos los estados del viaje (patrón State).
 *
 * <p>Cada estado concreto implementa esta interfaz y encapsula el
 * comportamiento específico de esa fase del ciclo de vida del viaje.
 * Las transiciones inválidas lanzan {@link IllegalStateException}.</p>
 *
 * @see Ride
 * @see com.ridehailing.domain.states.AbstractRideState
 */
public interface RideState {

    /** Solicita un nuevo viaje. */
    void requestRide(Ride ride);

    /** Asigna un conductor al viaje solicitado. */
    void assignDriver(Ride ride);

    /** Indica que el conductor ha llegado al punto de recogida. */
    void driverArrives(Ride ride);

    /** Inicia el viaje una vez el pasajero aborda el vehículo. */
    void startTrip(Ride ride);

    /** Marca el viaje como completado al llegar al destino. */
    void completeTrip(Ride ride);

    /** Cancela el viaje. Solo válido antes de iniciar el trayecto. */
    void cancel(Ride ride);
}
