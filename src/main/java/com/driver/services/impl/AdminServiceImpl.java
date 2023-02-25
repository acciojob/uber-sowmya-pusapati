package com.driver.services.impl;

import java.util.ArrayList;
import java.util.List;

import com.driver.model.Cab;
import com.driver.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.model.Admin;
import com.driver.model.Customer;
import com.driver.model.Driver;
import com.driver.repository.AdminRepository;
import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	AdminRepository adminRepository1;

	@Autowired
	DriverRepository driverRepository1;

	@Autowired
	CustomerRepository customerRepository1;

	@Override
	public void adminRegister(Admin admin) {
		//Save the admin in the database
		Admin newAdmin=new Admin();
		newAdmin.setUserName(admin.getUserName());
		newAdmin.setPassword(admin.getPassword());
        adminRepository1.save(newAdmin);

	}

	@Override
	public Admin updatePassword(Integer adminId, String password) {
		//Update the password of admin with given id
		Admin newAdmin=new Admin();
		Admin admin=adminRepository1.findById(adminId).get();
		newAdmin.setId(admin.getId());
		newAdmin.setUserName(admin.getUserName());
		newAdmin.setPassword(admin.getPassword());
		newAdmin.setPassword(password);
		adminRepository1.save(newAdmin);
		return newAdmin;

	}

	@Override
	public void deleteAdmin(int adminId){
		// Delete admin without using deleteById function
		adminRepository1.deleteById(adminId);

	}

	@Override
	public List<Driver> getListOfDrivers() {
		//Find the list of all drivers
		List<Driver> driverList=new ArrayList<>();
		for(Driver driver:driverRepository1.findAll())
		{
			driverList.add(driver);
		}
		return driverList;

	}

	@Override
	public List<Customer> getListOfCustomers() {
		//Find the list of all customers
		List<Customer> customerList=new ArrayList<>();
		for(Customer customer:customerRepository1.findAll())
		{
			customerList.add(customer);
		}
		return customerList;

	}

}
