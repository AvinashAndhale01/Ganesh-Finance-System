package com.finance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.finance.entity.Payment;
import com.finance.service.LoanApplicationDTO;
import com.finance.service.LoanApprovalDTO;
import com.finance.service.PaymentServiceImpl;

@RestController
@RequestMapping("/payments")
public class PaymentController {
	
	@Autowired
	private PaymentServiceImpl paymentService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	@PostMapping
	public ResponseEntity<String> processPayment(@RequestBody Payment payment) {
		String loanApplicationServiceUrl = "http://ganeshfinancemanagement-loanapplication/loans/"+payment.getLoanId();
		
		LoanApplicationDTO loanApplicationDTO= restTemplate.getForObject(loanApplicationServiceUrl, LoanApplicationDTO.class);
		if(loanApplicationDTO.getStatus().equals("Processed and Approved")) {
			paymentService.processPayment(payment);
			return new ResponseEntity<String>("Your payment is Completed with payment id:"+payment.getPaymentId(),HttpStatus.OK);
		}
		else {
			throw new RuntimeException("Loan is not Approved");
		}
//		paymentService.processPayment(payment);
//		return new ResponseEntity<String>("Your payment is Completed with payment id:"+payment.getPaymentId(),HttpStatus.OK);

	}
	
	@GetMapping
	public List<Payment> getAllPayments(){
		System.out.println("not running");
		return paymentService.getAllPayments();
	}
	@GetMapping("/{id}")
	public Payment getPaymentById(@PathVariable Long id) {
		return paymentService.getPaymentById(id);
	}
}
