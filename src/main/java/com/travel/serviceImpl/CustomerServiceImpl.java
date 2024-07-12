package com.travel.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.travel.bean.ApiResponse;
import com.travel.bean.CustomerDto;
import com.travel.bean.ResetPasswordDTO;
import com.travel.repository.CustomerRepository;
import com.travel.service.CustomerService;
 
@Service
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	OtpServiceImpl otpServiceImpl;
	
	public ApiResponse registerCustomer(CustomerDto customerDto){
		ApiResponse apiResponse = new ApiResponse();
		 CustomerDto existMail = customerRepository.findByEmail(customerDto.getEmail());
		    if (existMail != null) {
	            apiResponse.setStatus(true);
	            apiResponse.setStatusCode("200");
	            apiResponse.setMessage("Customer email already exists");
	            return apiResponse;
	        } 
		  CustomerDto existByPhoneNUmber = customerRepository.findByPhone(customerDto.getPhone());
		  if (existByPhoneNUmber != null) {
		        apiResponse.setStatus(true);
		        apiResponse.setStatusCode("200");
		        apiResponse.setMessage("Customer phone already exists");
		        return apiResponse;
		   }
		  if(!customerDto.getPassword().equals(customerDto.getConfirmPassword())) {
			  apiResponse.setStatus(true);
		        apiResponse.setStatusCode("200");
		        apiResponse.setMessage("Password not matched");
		        return apiResponse;
		  }
		CustomerDto saveCustomer= customerRepository.save(customerDto);
		sendCustomerSavedEmailNotification(saveCustomer);
		apiResponse.setStatus(true);
        apiResponse.setStatusCode("200");
        apiResponse.setMessage("Customer registered successfully");
        apiResponse.setData(saveCustomer);
		return apiResponse;
	}

	public ApiResponse getCustomerById(int id) {
		ApiResponse apiResponse = new ApiResponse();
		Optional<CustomerDto> optional=customerRepository.findById(id);
		if(optional.isPresent()) {
			apiResponse.setStatus(true);
			apiResponse.setStatusCode("200");
			apiResponse.setMessage("Customer fetched successfully with id "+id);
			apiResponse.setData(optional.get());
		}
		else {
			apiResponse.setStatus(false);
			apiResponse.setStatusCode("200");
			apiResponse.setMessage("Customer not found with id "+id);
			return apiResponse;
		}
		return apiResponse;
		
	}

	public ApiResponse getAllCustomers() {
		ApiResponse apiResponse = new ApiResponse();
	    List<CustomerDto> data =  customerRepository.findAll();
		apiResponse.setStatus(true);
		apiResponse.setStatusCode("200");
		apiResponse.setMessage("all Customers fetched successfully");
		apiResponse.setData(data);
		return apiResponse;
		
	}

	public ApiResponse updateCustomer(CustomerDto dto) {
		ApiResponse apiResponse = new ApiResponse();
	  Optional<CustomerDto> exist=customerRepository.findById(dto.getId());
	  CustomerDto existByPhoneNUmber = customerRepository.findByPhone(dto.getPhone());
	  if (existByPhoneNUmber != null) {
	        apiResponse.setStatus(true);
	        apiResponse.setStatusCode("200");
	        apiResponse.setMessage("Customer phone already exists");
	        return apiResponse;
	   }
	if(exist.isPresent()) {
		  CustomerDto Customer= exist.get();
		  Customer.setId(dto.getId());
		  Customer.setFirstName(dto.getFirstName());
		  Customer.setLastName(dto.getLastName());
		  Customer.setEmail(dto.getEmail());
		  Customer.setPhone(dto.getPhone());
		  Customer.setGender(dto.getGender());
		  Customer.setPassword(dto.getPassword());
		  Customer.setConfirmPassword(dto.getConfirmPassword());
		CustomerDto updateCustomer = customerRepository.save(Customer);
		sendCustomerUpdatedEmailNotification(updateCustomer);
		apiResponse.setStatus(true);
        apiResponse.setStatusCode("200");
        apiResponse.setMessage("Customer updated successfully");
        apiResponse.setData(updateCustomer);
		return apiResponse;
	 }
	else {
		apiResponse.setStatus(true);
        apiResponse.setStatusCode("200");
        apiResponse.setMessage("Customer not found in database");
        return apiResponse;
	}
   }

	public ApiResponse deleteCustomer(int id) {
		ApiResponse apiResponce = new ApiResponse();
        Optional<CustomerDto> dto=customerRepository.findById(id);
        if(dto!=null) {
        	customerRepository.delete(dto.get());
        	apiResponce.setStatus(true);
        	apiResponce.setStatusCode("200");
        	apiResponce.setMessage("Customer deleted successfully");
        	return apiResponce;
        }
        else {
        	apiResponce.setStatus(false);
        	apiResponce.setStatusCode("200");
        	apiResponce.setMessage("Customer not found in database");
        	return apiResponce;
		}
	}
	
	private SimpleMailMessage sendCustomerSavedEmailNotification(CustomerDto customerDto) {
		 CustomerDto SavedCustomer=customerRepository.findByEmail(customerDto.getEmail());
		 try {
		 if(SavedCustomer!=null){
				return otpServiceImpl.sendCustomerSavedEmail(SavedCustomer.getEmail());
			}else {
				return null;
			}
		}catch (Exception ex) {
		System.out.println(ex.getMessage());
		return null;
	  }	
	}
	
	private SimpleMailMessage sendCustomerUpdatedEmailNotification(CustomerDto updateCustomer) {
		CustomerDto updatedCustomer=customerRepository.findByEmail(updateCustomer.getEmail());
		 try {
		 if(updatedCustomer!=null){
				return otpServiceImpl.sendCustomerUpdatedEmail(updatedCustomer.getEmail());
			}else {
				return null;
			}
		}catch (Exception ex) {
		System.out.println(ex.getMessage());
		return null;
	  }	
	}

	@Override
	 public String resetPassword(ResetPasswordDTO resetPasswordDTO) {
        List<CustomerDto> customers = customerRepository.findAll();
        CustomerDto customerToUpdate = null;

        for (CustomerDto customer : customers) {
            if (customer.getPhone().equals(resetPasswordDTO.getPhoneNumber())) {
                customerToUpdate = customer;
                break;
            }
        }

        if (customerToUpdate == null) {
            return "Phone number not found";
        }

        if (!resetPasswordDTO.getNewPassword().equals(resetPasswordDTO.getConfirmPassword())) {
            return "Passwords do not match";
        }

        customerToUpdate.setPassword(resetPasswordDTO.getNewPassword());
        customerToUpdate.setConfirmPassword(resetPasswordDTO.getConfirmPassword());
        customerRepository.save(customerToUpdate);

        return "Password reset successfully";
    }
}

