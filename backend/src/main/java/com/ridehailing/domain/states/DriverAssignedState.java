package com.ridehailing.domain.states;

import com.ridehailing.domain.Ride;
import com.ridehailing.domain.RideState;

public class DriverAssignedState implements RideState {

    @Override
    public void requestRide(Ride ride) {
        System.out.println("Error: Ride already requested.");
    }

    @Override
    public void assignDriver(Ride ride) {
        System.out.println("Error: Driver already assigned.");
    }

    @Override
    public void driverArrives(Ride ride) {
        System.out.println("Driver is on the way.");
        ride.setState(new DriverArrivingState());
    }

    @Override
    public void startTrip(Ride ride) {
        System.out.println("Error: Driver has not arrived yet.");
    }

    @Override
    public void completeTrip(Ride ride) {
        System.out.println("Error: Trip has not started.");
    }

    @Override
    public void cancel(Ride ride) {
        System.out.println("Ride cancelled after driver assignment.");
        ride.setState(new CancelledState());
    }
}
