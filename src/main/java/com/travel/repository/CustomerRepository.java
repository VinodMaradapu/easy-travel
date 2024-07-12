package com.travel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.travel.bean.CustomerDto;
@Repository
public interface CustomerRepository extends JpaRepository<CustomerDto, Integer>{
	
	    @Query("SELECT c FROM CustomerDto c WHERE c.email = :email")
	    CustomerDto findByEmail(@Param("email") String email);
	    
	    @Query("SELECT c FROM CustomerDto c WHERE c.phone = :phone")
	    CustomerDto findByPhone(@Param("phone") String phone);

	    @Query("SELECT u FROM CustomerDto u WHERE (u.email = :emailOrPhone OR u.phone = :emailOrPhone) AND u.password = :password")
	    CustomerDto findByEmailorPhoneNumberAndPassword(@Param("emailOrPhone") String emailOrPhone, @Param("password") String password);
//
//	    @Query("SELECT c FROM CustomerDto c WHERE c.firstName = :firstName")
//	    boolean existsByName(@Param("firstName")String firstName);
//	    
//        @Query("SELECT c FROM CustomerDto c WHERE c.phone = :phone")
//        boolean existsByPhoneNumber(@Param("phone") String phone);
}
