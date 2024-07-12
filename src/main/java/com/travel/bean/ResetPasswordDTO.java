package com.travel.bean;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
@Data
public class ResetPasswordDTO {

	private String phoneNumber; 
	 @NotBlank(message = "New password is mandatory")
	    @Size(min = 6, message = "Password must be at least 6 characters long")
	    private String newPassword;

	    @NotBlank(message = "Confirm password is mandatory")
	    @Size(min = 6, message = "Password must be at least 6 characters long")
	    private String confirmPassword;
}
