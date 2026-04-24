package com.ridehailing.domain.states;

import com.ridehailing.domain.Ride;
import com.ridehailing.domain.RideState;

public class CompletedState implements RideState {

    @Override
    public void requestRide(Ride ride) {
        System.out.println("Error: Ride is already completed.");
    }

    @Override
    public void assignDriver(Ride ride) {
        System.out.println("Error: Ride is already completed.");
    }

    @Override
    public void driverArrives(Ride ride) {
        System.out.println("Error: Ride is already completed.");
    }

    @Override
    public void startTrip(Ride ride) {
        System.out.println("Error: Ride is already completed.");
    }

    @Override
    public void completeTrip(Ride ride) {
        System.out.println("Error: Ride is already completed.");
    }

    @Override
    public void cancel(Ride ride) {
        System.out.println("Error: Ride is already completed.");
    }
}
