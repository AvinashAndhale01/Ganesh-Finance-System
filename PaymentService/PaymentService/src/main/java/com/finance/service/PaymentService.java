package com.finance.service;

import com.finance.entity.Payment;

public interface PaymentService {
	
	Payment processPayment(Payment payment);
	Payment getPaymentById(Long id);
}
