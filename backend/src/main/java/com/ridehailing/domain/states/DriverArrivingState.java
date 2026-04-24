package com.ridehailing.domain.states;

import com.ridehailing.domain.Ride;
import com.ridehailing.domain.RideState;

public class DriverArrivingState implements RideState {

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
        System.out.println("Error: Driver is already arriving.");
    }

    @Override
    public void startTrip(Ride ride) {
        System.out.println("Trip started.");
        ride.setState(new InTripState());
    }

    @Override
    public void completeTrip(Ride ride) {
        System.out.println("Error: Trip has not started yet.");
    }

    @Override
    public void cancel(Ride ride) {
        System.out.println("Ride cancelled while driver was arriving.");
        ride.setState(new CancelledState());
    }
}
