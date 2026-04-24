package com.ridehailing.domain;

import com.ridehailing.domain.states.RequestingState;
import java.util.Locale;

public class Ride {

    private RideState currentState;
    private String stateName;

    public Ride() {
        this.currentState = new RequestingState();
        this.stateName = "REQUESTING";
    }

    public void setState(RideState state) {
        this.currentState = state;
        this.stateName = toStateName(state);
    }

    public String getStateName() {
        return stateName;
    }

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

    private String toStateName(RideState state) {
        String simpleName = state.getClass().getSimpleName();
        String rawName = simpleName.endsWith("State")
                ? simpleName.substring(0, simpleName.length() - "State".length())
                : simpleName;
        return rawName.replaceAll("([a-z])([A-Z])", "$1_$2").toUpperCase(Locale.ROOT);
    }
}
