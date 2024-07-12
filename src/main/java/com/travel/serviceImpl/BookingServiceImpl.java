package com.travel.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.bean.BookingDto;
import com.travel.bean.CustomerDto;
import com.travel.repository.BookingRepository;
import com.travel.repository.CustomerRepository;
import com.travel.service.BookingService;

@Service
public class BookingServiceImpl implements BookingService{

	@Autowired
	BookingRepository bookingRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Override
	public BookingDto bookRide(BookingDto bookingDto) {
		List<CustomerDto> customerDto=customerRepository.findAll();
//		 if (!captainDto.isEmpty()) {
//	          String captainUserName = captainDto.get(0).getUserName();
//	          bookingDto.setCaptainName(captainUserName);
//		 }
//		 List<Customer> customer=customerRepostory.findAll();
//		 if (!customer.isEmpty()) {
//	          String customerName = customer.get(0).getFirstName();
//	          bookingDto.setCustomerName(customerName);
//		 }
        long roundedDistance = Math.round(bookingDto.getDistance());
		long calculatedPayment = calculatePayment(roundedDistance);
		bookingDto.setPayment(calculatedPayment);
		  return bookingRepository.save(bookingDto);
	}
	private long calculatePayment(long roundedDistance) {
		int rupees=20;
		long payment=roundedDistance*rupees;
		return payment;
	}
}
	

