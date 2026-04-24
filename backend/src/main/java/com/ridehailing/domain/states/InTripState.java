package com.ridehailing.domain.states;

import com.ridehailing.domain.Ride;
import com.ridehailing.domain.RideState;

public class InTripState implements RideState {

    @Override
    public void requestRide(Ride ride) {
        System.out.println("Error: Already in a trip.");
    }

    @Override
    public void assignDriver(Ride ride) {
        System.out.println("Error: Already in a trip.");
    }

    @Override
    public void driverArrives(Ride ride) {
        System.out.println("Error: Already in a trip.");
    }

    @Override
    public void startTrip(Ride ride) {
        System.out.println("Error: Trip already started.");
    }

    @Override
    public void completeTrip(Ride ride) {
        System.out.println("Trip completed.");
        ride.setState(new CompletedState());
    }

    @Override
    public void cancel(Ride ride) {
        System.out.println("Error: Cannot cancel a ride that is in progress.");
    }
}
