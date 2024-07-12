package com.travel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.travel.bean.ResetPasswordDTO;
import com.travel.service.CustomerService;


@RestController
public class PasswordController {

	  @Autowired
	    private CustomerService customerService;

	    @PostMapping("/reset")
	    public String resetPassword( @RequestBody ResetPasswordDTO resetPasswordDTO) {
	    	return customerService.resetPassword(resetPasswordDTO);
	        }
	       
	    
}
