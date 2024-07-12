package com.travel.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.travel.bean.ApiResponse;
import com.travel.bean.CustomerDto;
import com.travel.service.CustomerService;

@RestController
public class CustomerController {
	
	@Autowired
	CustomerService captainService;
	
	@PostMapping("/registerCustomer")
	public ApiResponse saveCustomer(@RequestBody CustomerDto customer){
		return captainService.registerCustomer(customer);
	}
	
	@GetMapping("/getAllCustomers")
	public ApiResponse getCustomer(){
		return captainService.getAllCustomers();
	}
	@GetMapping("/getCustomerById/{id}")
	public ApiResponse getByIdCustomer(@PathVariable int id){
		return captainService.getCustomerById(id);
	}
	@DeleteMapping("delete/{id}")
	public ApiResponse deleteById(@PathVariable int id){
		return captainService.deleteCustomer(id);
	}
	@PutMapping("/update/{id}")
	public ApiResponse updateCustomer(@RequestBody CustomerDto customer){
		return captainService.updateCustomer(customer);
	}
}
