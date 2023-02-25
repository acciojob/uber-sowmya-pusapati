package com.driver.services.impl;

import com.driver.model.*;
import com.driver.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;
import com.driver.repository.TripBookingRepository;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository2;

	@Autowired
	DriverRepository driverRepository2;

	@Autowired
	TripBookingRepository tripBookingRepository2;

	@Override
	public void register(Customer customer) {
		//Save the customer in database

		customerRepository2.save(customer);
	}

	@Override
	public void deleteCustomer(Integer customerId) {
		// Delete customer without using deleteById function
		Customer customer=customerRepository2.findById(customerId).get();
		List<TripBooking> tripBookingList=customer.getTripBookingList();
		for(TripBooking trip:tripBookingList)
		{
			Driver driver=trip.getDriver();
			Cab cab=driver.getCab();
			cab.setAvailable(true);
			driverRepository2.save(driver);
			trip.setTripStatus(TripStatus.CANCELED);
		}
		customerRepository2.delete(customer);



	}

	@Override
	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception{
		//Book the driver with lowest driverId who is free (cab available variable is Boolean.TRUE). If no driver is available, throw "No cab available!" exception
		//Avoid using SQL query
		TripBooking tripBooking=new TripBooking();
		Customer customer=customerRepository2.findById(customerId).get();
		List<TripBooking> bookingList=customer.getTripBookingList();
		List<Driver> driverList=driverRepository2.findAll();
		int id=Integer.MAX_VALUE;
		for(Driver driver:driverList)
		{
			if(driver.getDriverId()<id)
			{
				id= driver.getDriverId();
			}

		}
		Driver driver1=driverRepository2.findById(id).get();
		List<TripBooking> bookingList1=driver1.getTripBookingList();
		Cab cab=driver1.getCab();
		if(!cab.isAvailable())
		{
			throw new Exception("No cab available!");
		}


			tripBooking.setFromLocation(fromLocation);
			tripBooking.setToLocation(toLocation);
			tripBooking.setDistanceInKm(distanceInKm);
			tripBooking.setTripStatus(TripStatus.CONFIRMED);
			tripBooking.setCustomer(customer);
			tripBooking.setDriver(driver1);
			int rate=driver1.getCab().getPerKmRate();
			tripBooking.setBill(distanceInKm*rate);

			driver1.getCab().setAvailable(false);
			driverRepository2.save(driver1);

			customer.getTripBookingList().add(tripBooking);
			customerRepository2.save(customer);



        return tripBooking;

	}

	@Override
	public void cancelTrip(Integer tripId){
		//Cancel the trip having given trip Id and update TripBooking attributes accordingly
		 TripBooking trip=tripBookingRepository2.findById(tripId).get();

		 trip.setTripStatus(TripStatus.CANCELED);
		 trip.setBill(0);
		 trip.getDriver().getCab().setAvailable(true);
		 tripBookingRepository2.save(trip);

		 }


	@Override
	public void completeTrip(Integer tripId){
		//Complete the trip having given trip Id and update TripBooking attributes accordingly
		TripBooking trip=tripBookingRepository2.findById(tripId).get();
		trip.setTripStatus(TripStatus.COMPLETED);
		trip.getDriver().getCab().setAvailable(true);
		tripBookingRepository2.save(trip);



	}
}
