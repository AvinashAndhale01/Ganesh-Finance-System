package com.finance.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.finance.entity.ReportServiceEntity;
import com.finance.repository.ReportServiceRepository;

@Service
public class ReportService {
	
	@Autowired
	private ReportServiceRepository reportServiceRepository;
	
	@Autowired
	private RestTemplate rt;
	
	String Customer_URL= "http://identity-service/auth/user/";
	String LoanApplication_URL="http://ganeshfinancemanagement-loanapplication/loans/";
	String Payment_URL="http://paymentservice/payments/";
	
	public ReportServiceEntity generateReport(ReportServiceEntity report) {
		
		UserLoginDTO userLoginDTO= rt.getForObject(Customer_URL+report.getCustomerId(), UserLoginDTO.class);
		report.setName(userLoginDTO.getUsername());
		report.setEmail(userLoginDTO.getEmail());
		
		LoanApplicationDTO loanApplicationDTO= rt.getForObject(LoanApplication_URL+report.getLoanId(),LoanApplicationDTO.class);
		report.setLoanAmount(loanApplicationDTO.getLoanAmount());
		report.setLoanType(loanApplicationDTO.getLoanType());
		report.setLoanStatus(loanApplicationDTO.getStatus());
		
		PaymentDTO paymentDTO= rt.getForObject(Payment_URL+report.getPaymentId(), PaymentDTO.class);
		report.setDueAmount(paymentDTO.getDueAmount());;
		
		return reportServiceRepository.save(report);
		
	}
	
	public List<ReportServiceEntity> getAllReports(){
		return reportServiceRepository.findAll();
	}
	
	public ReportServiceEntity getReportById(Long id) {
		return reportServiceRepository.findById(id).orElseThrow(() -> new RuntimeException("Report not found"));
	}
	

}
