package com.ganesh.service;

import java.util.List;

import com.ganesh.entity.LoanApproval;

public interface LoanApprovalServiceInterface {
	public LoanApproval processLoanApproval(LoanApproval loanApproval);
	public List<LoanApproval> getAllLoanApprovals();
	
}
