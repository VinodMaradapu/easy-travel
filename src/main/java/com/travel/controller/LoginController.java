package com.travel.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.travel.bean.ApiResponse;
import com.travel.bean.SendOtpPhone;
import com.travel.bean.VerifyOtpDetails;
import com.travel.service.LoginService;
import com.travel.bean.LoginCustomer;
import com.travel.bean.SendOtpEmail;


@RestController
public class LoginController {
	
	@Autowired
	LoginService loginService;
	
	@PostMapping("/login")
	public ApiResponse login(@RequestBody LoginCustomer customer) {
		return loginService.login(customer);
	}
	@PostMapping("/sendOTPtophone")
	public String sendOtpPhone(@RequestBody SendOtpPhone sendOtpPhone) {
		return loginService.sendOtpByPhone(sendOtpPhone);
	}
	@PostMapping("/sendOTPtoMail")
	public String sendOtpByEmail(@RequestBody SendOtpEmail sendOtpRequest) {
		return loginService.sendOtpByEmail(sendOtpRequest);
	}
	@PostMapping("/verifyOTPPhone")
	public String verifyOtpPhone(@RequestBody VerifyOtpDetails otpDetails) {
		return loginService.verifyOtpPhone(otpDetails);
	}
}
