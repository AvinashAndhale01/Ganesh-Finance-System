package com.finance.service;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PaymentDTO {
	private Long paymentId;
    private Long loanId;
    private double amountPaid;
    private LocalDate paymentDate;
    private String paymentMode;
    private double dueAmount;
	
}
