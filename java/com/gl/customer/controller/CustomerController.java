package com.gl.customer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gl.customer.entities.Customer;
import com.gl.customer.service.CustomerService;


@Controller
@RequestMapping("/customers")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping("/list")
	public String listCustomers(Model model) {
		List<Customer> customers= customerService.findAll();
		model.addAttribute("Customers",customers);
		
		return "list-Customers";
	}
	
	@RequestMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		
		Customer theCustomer = new Customer();
		
		theModel.addAttribute("Customer",theCustomer);
		
		return "Customer-form";
	}
	
	@RequestMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int theId, Model theModel) {
		
		Customer theCustomer = customerService.findById(theId);
		
		theModel.addAttribute("Customer",theCustomer);
		
		return "Customer-form";
	}
	
	@PostMapping("/save")
	public String saveCustomer(@RequestParam("id") int id, @RequestParam("first_name") String first_name, @RequestParam("last_name") String last_name, @RequestParam("email") String email) {
		
		System.out.println(id);
		Customer theCustomer;
		if(id!=0) {
			theCustomer = customerService.findById(id);
			theCustomer.setFirst_name(first_name);
			theCustomer.setLast_name(last_name);
			theCustomer.setEmail(email);
		}else
			theCustomer = new Customer(first_name, last_name, email);
		customerService.save(theCustomer);
		
		return "redirect:/customers/list";
		
	}
	
	@RequestMapping("/delete")
	public String delete(@RequestParam("customerId") int theId) {
		customerService.deleteById(theId);
		
		return "redirect:/customers/list";
	}
	
	@RequestMapping("/search")
	public String search(@RequestParam("first_name") String first_name, @RequestParam("last_name") String last_name, @RequestParam("email") String email, Model theModel) {
		if (first_name.trim().isEmpty() && last_name.trim().isEmpty() && email.trim().isEmpty()) {
			return "redirect:/customers/list";
		} else {
			List<Customer> theCustomers = customerService.searchBy(first_name,last_name, email);
			
			theModel.addAttribute("Customers",theCustomers);
			
			return "list-Customers";
		}
	}

}
