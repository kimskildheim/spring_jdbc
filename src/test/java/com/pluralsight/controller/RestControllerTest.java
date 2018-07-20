package com.pluralsight.controller;

import java.util.List;

import org.junit.Ignore;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.pluralsight.model.Ride;

import org.junit.Test;

public class RestControllerTest {

	@Test(timeout = 6000)
	public void testCreateRide(){
		RestTemplate restTemplate = new RestTemplate();

		Ride ride = new Ride();
		ride.setName("YellowFork Trail");
		ride.setDuration(38);

		restTemplate.postForObject("http://localhost:8080/ride_tracker/ride", ride, Ride.class);

        System.out.println("Ride: " + ride);
	}

	@Test(timeout=6000)
	public void testGetRides() {
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<List<Ride>> ridesResponse = restTemplate.exchange(
				"http://localhost:8080/ride_tracker/rides", HttpMethod.GET,
				null, new ParameterizedTypeReference<List<Ride>>() {
				});
		List<Ride> rides = ridesResponse.getBody();

		for (Ride ride : rides) {
			System.out.println("Ride name: " + ride.getName());
		}
	}

	@Test(timeout=6000)
    public void testGetRide() {
	    RestTemplate restTemplate = new RestTemplate();

	    Ride ride = restTemplate.getForObject("http://localhost:8080/ride_tracker/ride/3", Ride.class);

        System.out.println("Ride name: " + ride.getName());
    }

    @Test(timeout=6000)
    public void testUpdateRide() {
        RestTemplate restTemplate = new RestTemplate();

        Ride ride = restTemplate.getForObject("http://localhost:8080/ride_tracker/ride/1", Ride.class);

        ride.setDuration(ride.getDuration() + 1);

        restTemplate.put("http://localhost:8080/ride_tracker/ride/", ride);

        System.out.println("Ride name: " + ride.getName());
    }

    @Test(timeout=6000)
    public void testBatchRide() {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getForObject("http://localhost:8080/ride_tracker/batch", Object.class);
    }

    @Ignore
    @Test(timeout=6000)
    public void testDeleteRide() {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.delete("http://localhost:8080/ride_tracker/delete/4");
    }

    @Test(timeout=6000)
    public void testExceptionRide() {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getForObject("http://localhost:8080/ride_tracker/test", Ride.class);
    }
}
