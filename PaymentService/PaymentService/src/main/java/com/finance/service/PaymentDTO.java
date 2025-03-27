package com.finance.service;

import lombok.Data;

@Data
public class PaymentDTO {
	
	private Long loanId;
	
	private double amountPaid;
	
	private String paymentMode;
	
	/*
	 * public double getAmountPaid() { // TODO Auto-generated method stub return 0;
	 * }
	 */
}
