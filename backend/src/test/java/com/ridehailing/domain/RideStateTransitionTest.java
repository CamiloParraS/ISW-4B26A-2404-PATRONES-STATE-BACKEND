package com.ridehailing.domain;

import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Pruebas unitarias para validar las transiciones del patrón State.
 */
class RideStateTransitionTest {

    @Test
    void happyPath_completesSuccessfully() {
        Ride ride = new Ride();
        ride.assignDriver();
        ride.driverArrives();
        ride.startTrip();
        ride.completeTrip();
        assertEquals("COMPLETED", ride.getStateName());
    }

    @Test
    void cannotStartTrip_beforeDriverArrives() {
        Ride ride = new Ride();
        ride.assignDriver();
        assertThrows(IllegalStateException.class, ride::startTrip);
    }

    @Test
    void cannotCompleteTrip_fromRequesting() {
        Ride ride = new Ride();
        assertThrows(IllegalStateException.class, ride::completeTrip);
    }

    @Test
    void cannotAssignDriver_afterCancellation() {
        Ride ride = new Ride();
        ride.cancel();
        assertThrows(IllegalStateException.class, ride::assignDriver);
    }

    @Test
    void transitionHistory_recordsAllStates() {
        Ride ride = new Ride();
        ride.assignDriver();
        ride.driverArrives();
        ride.startTrip();
        ride.completeTrip();
        assertEquals(
                List.of("REQUESTING", "DRIVER_ASSIGNED", "DRIVER_ARRIVING", "IN_TRIP", "COMPLETED"),
                ride.getTransitionHistory());
    }

    @Test
    void cancelFromRequesting_movesToCancelled() {
        Ride ride = new Ride();
        ride.cancel();
        assertEquals("CANCELLED", ride.getStateName());
    }

    @Test
    void cannotCancel_duringTrip() {
        Ride ride = new Ride();
        ride.assignDriver();
        ride.driverArrives();
        ride.startTrip();
        assertThrows(IllegalStateException.class, ride::cancel);
    }
}
