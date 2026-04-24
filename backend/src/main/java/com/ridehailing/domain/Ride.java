package com.ridehailing.domain;

import com.ridehailing.domain.states.RequestingState;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Context class of the State pattern. Delegates every action to the current RideState, which
 * decides whether the transition is valid.
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
     * @param stateName nombre del estado actual
     * @param history historial previo de transiciones
     */
    public Ride(RideState initialState, String stateName, List<String> history) {
        this.currentState = initialState;
        this.stateName = stateName;
        this.transitionHistory = new ArrayList<>(history);
    }

    public void setState(RideState state) {
        this.currentState = state;
        String simpleName = state.getClass().getSimpleName();
        String rawName = simpleName.endsWith("State")
                ? simpleName.substring(0, simpleName.length() - "State".length())
                : simpleName;
        this.stateName = rawName.replaceAll("([a-z])([A-Z])", "$1_$2").toUpperCase(Locale.ROOT);
        this.transitionHistory.add(this.stateName);
    }

    public String getStateName() {
        return stateName;
    }

    public List<String> getTransitionHistory() {
        return Collections.unmodifiableList(transitionHistory);
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
}
