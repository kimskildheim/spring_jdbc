package com.pluralsight.service;

import java.util.List;

import com.pluralsight.model.Ride;

public interface RideService {

	Ride getRide(Integer id);

	List<Ride> getRides();

	Ride createRide(Ride ride);

    Ride updateRide(Ride ride);

    void batch();

    void deleteRide(Integer id);
}