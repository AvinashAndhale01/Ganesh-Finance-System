package com.finance.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class ReportServiceEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "Customer name is mandatory")
	private Long customerId;
	
	@NotNull(message = "LoanId is mandatory")
	private Long loanId;

	@NotNull(message="Loan Amount is mandatory")
	private Double loanAmount;
	
	@NotNull(message = "loan Status is mandatory")
	private String loanStatus;
	
	private double dueAmount;
	
	private String loanType;
	
	private String name;
	
	private String email;
	
	
	
	private String paymentId;
}
