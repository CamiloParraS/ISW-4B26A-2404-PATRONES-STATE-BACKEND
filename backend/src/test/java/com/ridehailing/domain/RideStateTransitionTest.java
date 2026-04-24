package com.ridehailing.domain;

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
