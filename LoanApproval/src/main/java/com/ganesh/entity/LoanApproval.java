package com.ganesh.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class LoanApproval {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "Loan application Id is required" )
	private Long loanApplicationId;
	
	@Min(value = 300, message="Credit Score should be at least 300")
	@Max(value =900, message ="Invalid CreditScore")
	private int creditScore;
	
	private Boolean isApproved;
	
	private String rejectionReason;
	
	private double loanAmount;
	
	private String loanType;
	
	private Long customerId;
	

}
