package com.travel.service;

import com.travel.bean.ApiResponse;
import com.travel.bean.CustomerDto;
import com.travel.bean.LoginCustomer;
import com.travel.bean.SendOtpEmail;
import com.travel.bean.SendOtpPhone;
import com.travel.bean.VerifyOtpDetails;

public interface LoginService {

	String sendOtpByEmail(SendOtpEmail sendOtpRequest);

	String sendOtpByPhone(SendOtpPhone sendOtpPhone);

	String verifyOtpPhone(VerifyOtpDetails otpDetails);

	ApiResponse login(LoginCustomer loginCaptain);


}
