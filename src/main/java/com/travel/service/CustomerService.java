package com.travel.service;

import com.travel.bean.ApiResponse;
import com.travel.bean.CustomerDto;
import com.travel.bean.ResetPasswordDTO;

public interface CustomerService {

	ApiResponse registerCustomer(CustomerDto customer);

	ApiResponse getAllCustomers();

	ApiResponse getCustomerById(int id);

	ApiResponse deleteCustomer(int id);

	ApiResponse updateCustomer(CustomerDto customer);

	String resetPassword(ResetPasswordDTO resetPasswordDTO);


}
