package com.finance.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class LoanApplication {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;  
	
	private Long customerId;
	
	@NotBlank(message="Loan type is required")
	private String loanType;
	
	@Min(value=10000, message= "Loan amount must be at least 10000")
	private double loanAmount;
	
	private String status = "Pending";
	
	private String Email;
	
//	private String phone;
}
