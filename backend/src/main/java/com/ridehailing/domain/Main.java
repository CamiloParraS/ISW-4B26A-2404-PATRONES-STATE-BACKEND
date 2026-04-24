package com.ridehailing.domain;

public class Main {

    public static void main(String[] args) {
        System.out.println("Scenario A - Happy path");
        Ride ride = new Ride();
        ride.assignDriver();
        ride.driverArrives();
        ride.startTrip();
        ride.completeTrip();
        ride.cancel();

        System.out.println();
        System.out.println("Scenario B - Cancel mid-flow");
        Ride ride2 = new Ride();
        ride2.assignDriver();
        ride2.cancel();
        ride2.startTrip();
    }
}
