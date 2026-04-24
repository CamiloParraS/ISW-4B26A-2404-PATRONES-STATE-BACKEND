package com.ridehailing.domain;

/**
 * Contract for all ride states (State pattern). Each concrete state implements every method
 * explicitly - valid transitions delegate to Ride#setState, invalid ones throw
 * IllegalStateException with a descriptive message.
 */
public interface RideState {

    void assignDriver(Ride ride);

    void driverArrives(Ride ride);

    void startTrip(Ride ride);

    void completeTrip(Ride ride);

    void cancel(Ride ride);
}
