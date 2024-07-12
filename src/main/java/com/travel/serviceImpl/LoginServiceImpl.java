package com.travel.serviceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.bean.ApiResponse;
import com.travel.bean.CustomerDto;
import com.travel.bean.SendOtpPhone;
import com.travel.bean.VerifyOtpDetails;
import com.travel.bean.LoginCustomer;
import com.travel.bean.SendOtpEmail;
import com.travel.bean.util.JWTService;
import com.travel.repository.CustomerRepository;
import com.travel.repository.LoginRepository;
import com.travel.repository.VerifyOtpDetailsRepository;
import com.travel.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService{
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	LoginRepository loginRepository;
	
	@Autowired
	VerifyOtpDetailsRepository verifyOtpDetailsRepository;
	 
	@Autowired
	OtpServiceImpl otpService;
	
	@Autowired
	JWTService jwtservice;
	
    
	public String sendOtpByEmail(SendOtpEmail sendOtpEmail) {
	    CustomerDto customer = customerRepository.findByEmail(sendOtpEmail.getEmail());
	    try {
	        if (customer != null) {
	            String otp = otpService.generateOtp();
	            otpService.sendOtpByEmail(customer.getEmail(), otp);
	            VerifyOtpDetails verifyOtp = new VerifyOtpDetails();
	            verifyOtp.setEmail(customer.getEmail());
	            verifyOtp.setOtp(otp);
	            verifyOtpDetailsRepository.save(verifyOtp);
	            return "otp sent to email successfully";
	        } else {
	            System.out.println("Email not found in database: " + sendOtpEmail.getEmail());
	            return null;
	        }
	    } catch (Exception ex) {
	        System.out.println(ex.getMessage());
	        return null;
	    }
     }
	public String sendOtpByPhone(SendOtpPhone sendOtpPhone) {
		CustomerDto customer=customerRepository.findByPhone(sendOtpPhone.getPhone());
		try {
			if(customer!=null){
		      String otp = otpService.generateOtp();
		      otpService.sendOtpByPhone(customer.getPhone(), otp);
		      VerifyOtpDetails verifyOtp=new VerifyOtpDetails();
			  verifyOtp.setPhone(customer.getPhone());
			  verifyOtp.setOtp(otp);
			  verifyOtpDetailsRepository.save(verifyOtp);
	          return "otp sent to phone number "+customer.getPhone() + "is "+otp;
		}else{
				System.out.println("phone number not found in database");
				return null;
			}
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public String verifyOtpPhone(VerifyOtpDetails otpDetails) {
	    try {
	        VerifyOtpDetails details = verifyOtpDetailsRepository.findByPhoneWithOtp(otpDetails.getPhone(), otpDetails.getOtp());
	        if (details == null) {
	            return "invalid otp ";
	        }else {
	            return "otp verified successfully";
	        }
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        return null;
	    }
	}

	public VerifyOtpDetails verifyOtpEmail(VerifyOtpDetails otpDetails) {
		try {
		VerifyOtpDetails details=verifyOtpDetailsRepository.findByEmailWithOtp(otpDetails.getEmail(), otpDetails.getOtp());
		if (details == null) {
			System.out.println("invalid otp ");
			return details;
	        }else{
			System.out.println("otp verified sucessfully");
			return otpDetails;
	     	}
		}catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	public ApiResponse login(LoginCustomer customer) {
		ApiResponse apiResponce=new ApiResponse();
		try {
			CustomerDto customerDto=customerRepository.findByEmailorPhoneNumberAndPassword(customer.getEmailOrPhone(),customer.getPassword());
			if(customerDto!=null) {
	            String token = jwtservice.generateToken(customerDto.getEmail(),customerDto.getFirstName());
	            apiResponce.setStatus(true);
	            apiResponce.setStatusCode("200");
	            apiResponce.setMessage("successfully token generated");
	            apiResponce.setData(token);
	            return apiResponce;
			}else {
				apiResponce.setStatus(false);
	            apiResponce.setStatusCode("200");
	            apiResponce.setMessage("invalid data");
	            return apiResponce;
			}
		} catch (Exception e) {
			apiResponce.setStatus(false);
            apiResponce.setStatusCode("500");
            apiResponce.setMessage(e.getMessage());
            return apiResponce;
		}
	}
	
}
