package com.ridehailing.domain;

public interface RideState {
    void requestRide(Ride ride);

    void assignDriver(Ride ride);

    void driverArrives(Ride ride);

    void startTrip(Ride ride);

    void completeTrip(Ride ride);

    void cancel(Ride ride);
}
