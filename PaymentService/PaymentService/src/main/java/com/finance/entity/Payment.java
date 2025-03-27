package com.finance.entity;



import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Payment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long paymentId;
	
	@Column(nullable = false)
	private Long loanId;
	
	@Column(nullable = false)
	private double amountPaid;
	
	@Column(nullable = false)
	private LocalDate paymentDate;
	
	@Column(nullable = false)
	private String paymentMode;
	
	
	private double dueAmount;
}
