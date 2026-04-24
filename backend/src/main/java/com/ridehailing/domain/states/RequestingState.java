package com.ridehailing.domain.states;

import com.ridehailing.domain.Ride;
import com.ridehailing.domain.RideState;

public class RequestingState implements RideState {

    @Override
    public void requestRide(Ride ride) {
        System.out.println("Ride is already being requested.");
    }

    @Override
    public void assignDriver(Ride ride) {
        System.out.println("Driver assigned.");
        ride.setState(new DriverAssignedState());
    }

    @Override
    public void driverArrives(Ride ride) {
        System.out.println("Error: Driver not yet assigned.");
    }

    @Override
    public void startTrip(Ride ride) {
        System.out.println("Error: Trip cannot start without a driver.");
    }

    @Override
    public void completeTrip(Ride ride) {
        System.out.println("Error: No active trip to complete.");
    }

    @Override
    public void cancel(Ride ride) {
        System.out.println("Ride cancelled during request.");
        ride.setState(new CancelledState());
    }
}
