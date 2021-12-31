package com.gl.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gl.customer.entities.Customer;

@Service
public interface CustomerService {

		List<Customer> findAll();
		
		Customer findById(int id);
		
		void save(Customer customer);
		
		void deleteById(int id);
		
		List<Customer> searchBy(String first_name, String last_name, String email);
		
}

