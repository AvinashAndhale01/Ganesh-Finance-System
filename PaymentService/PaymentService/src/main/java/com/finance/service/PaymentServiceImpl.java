package com.finance.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.finance.entity.Payment;
import com.finance.repository.PaymentServiceRepository;

@Service
public class PaymentServiceImpl implements PaymentService {
	
	@Autowired
	private PaymentServiceRepository paymentRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	public Payment processPayment(Payment payment) {
		
		
		String loanApplicationServiceUrl = "http://ganeshfinancemanagement-loanapplication/loans/";
		
		LoanApplicationDTO loanApplicationDTO= restTemplate.getForObject(loanApplicationServiceUrl+payment.getLoanId(), LoanApplicationDTO.class);
		if(loanApplicationDTO==null) {
			throw new RuntimeException("Loan Application not found for id:"+payment.getLoanId());
		}
		
		if("Closed".equalsIgnoreCase(loanApplicationDTO.getStatus())) {
			throw new RuntimeException("Loan is already closed for Application id:"+payment.getLoanId());
		}
		double loanAmount=loanApplicationDTO.getLoanAmount(); 
		double paidAmount=payment.getAmountPaid();
		
		double totalPayments = paymentRepository.findByLoanId(payment.getLoanId())
								.stream().mapToDouble(Payment::getAmountPaid).sum();
		
		double remainingAmount = loanAmount -totalPayments;
		
		if(payment.getAmountPaid()>remainingAmount) {
			throw new RuntimeException("Payment exceeds the remaining loan amount. Please pay only: "+ remainingAmount);
		}
		
		payment.setDueAmount(remainingAmount-payment.getAmountPaid());
		payment.setPaymentDate(LocalDate.now());
		
		remainingAmount-=payment.getAmountPaid();
		
		if(remainingAmount==0) {
			loanApplicationDTO.setStatus("Closed");
			restTemplate.put(loanApplicationServiceUrl+"/update/"+loanApplicationDTO.getStatus()+"/"+loanApplicationDTO.getId(), loanApplicationDTO);
		}
			return paymentRepository.save(payment);
	       
	}
	@Override
	public Payment getPaymentById(Long id) {
		return paymentRepository.findById(id).orElseThrow(() -> new RuntimeException("Payment not found with ID: "+id));
	}
	
	public List<Payment> getAllPayments(){
		return paymentRepository.findAll();
	}
	
	public List<Payment> getPaymentsByLoanId(Long loanId){
		return paymentRepository.findByLoanId(loanId);
	}
	
}
