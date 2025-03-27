package com.finance.service;

import java.util.List;

import com.finance.entity.LoanApplication;

public interface LoanApplicationService {
	
	LoanApplication createApplication(LoanApplication application);
	List<LoanApplication> getAllLoanApplication();
	LoanApplication getLoanApplicationById(Long id);
	public void updateStatus(String status, Long applicationId);
	public List<LoanApplication> getAllByCustomerId(Long customerId);
	
}
