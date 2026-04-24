package com.ridehailing.service;

import com.ridehailing.domain.RideState;
import com.ridehailing.domain.states.CancelledState;
import com.ridehailing.domain.states.CompletedState;
import com.ridehailing.domain.states.DriverArrivingState;
import com.ridehailing.domain.states.DriverAssignedState;
import com.ridehailing.domain.states.InTripState;
import com.ridehailing.domain.states.RequestingState;

public final class RideStateMapper {

    private RideStateMapper() {}

    public static RideState fromStateName(String stateName) {
        return switch (stateName) {
            case "REQUESTING" -> new RequestingState();
            case "DRIVER_ASSIGNED" -> new DriverAssignedState();
            case "DRIVER_ARRIVING" -> new DriverArrivingState();
            case "IN_TRIP" -> new InTripState();
            case "COMPLETED" -> new CompletedState();
            case "CANCELLED" -> new CancelledState();
            default -> throw new IllegalArgumentException(
                    "Unknown ride state: '" + stateName + "'");
        };
    }
}
