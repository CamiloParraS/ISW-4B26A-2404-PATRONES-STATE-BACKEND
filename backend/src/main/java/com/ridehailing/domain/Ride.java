package com.ridehailing.domain;

import com.ridehailing.domain.states.RequestingState;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Clase Contexto del patrón State.
 *
 * <p>Representa un viaje que progresa a través de un ciclo de vida definido:
 * Solicitando → Conductor Asignado → Conductor en Camino → En Viaje → Completado.
 * Un viaje también puede ser cancelado desde cualquier estado previo al inicio
 * del trayecto.</p>
 *
 * <p>El contexto delega todas las acciones a su {@link RideState estado actual},
 * el cual determina si una transición es válida y la ejecuta invocando
 * {@link #setState(RideState)}.</p>
 */
public class Ride {

    private RideState currentState;
    private String stateName;
    private final List<String> transitionHistory;

    /** Crea un viaje nuevo en estado inicial REQUESTING. */
    public Ride() {
        this.currentState = new RequestingState();
        this.stateName = "REQUESTING";
        this.transitionHistory = new ArrayList<>();
        this.transitionHistory.add("REQUESTING");
    }

    /**
     * Reconstruye un viaje existente desde la capa de persistencia.
     *
     * @param initialState estado actual del viaje
     * @param stateName    nombre del estado actual
     * @param history      historial previo de transiciones
     */
    public Ride(RideState initialState, String stateName, List<String> history) {
        this.currentState = initialState;
        this.stateName = stateName;
        this.transitionHistory = new ArrayList<>(history);
    }

    /**
     * Establece un nuevo estado para el viaje y registra la transición
     * en el historial.
     *
     * @param state el nuevo estado del viaje
     */
    public void setState(RideState state) {
        this.currentState = state;
        this.stateName = toStateName(state);
        this.transitionHistory.add(this.stateName);
    }

    /** Retorna el nombre del estado actual en formato UPPER_SNAKE_CASE. */
    public String getStateName() {
        return stateName;
    }

    /** Retorna una vista inmodificable del historial de transiciones. */
    public List<String> getTransitionHistory() {
        return Collections.unmodifiableList(transitionHistory);
    }

    // --- Acciones delegadas al estado actual ---

    public void requestRide() {
        currentState.requestRide(this);
    }

    public void assignDriver() {
        currentState.assignDriver(this);
    }

    public void driverArrives() {
        currentState.driverArrives(this);
    }

    public void startTrip() {
        currentState.startTrip(this);
    }

    public void completeTrip() {
        currentState.completeTrip(this);
    }

    public void cancel() {
        currentState.cancel(this);
    }

    /**
     * Convierte el nombre simple de la clase del estado a formato UPPER_SNAKE_CASE.
     * Ejemplo: DriverAssignedState → DRIVER_ASSIGNED
     */
    private String toStateName(RideState state) {
        String simpleName = state.getClass().getSimpleName();
        String rawName = simpleName.endsWith("State")
                ? simpleName.substring(0, simpleName.length() - "State".length())
                : simpleName;
        return rawName.replaceAll("([a-z])([A-Z])", "$1_$2").toUpperCase(Locale.ROOT);
    }
}
