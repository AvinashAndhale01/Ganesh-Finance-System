package com.ganesh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ganesh.entity.LoanApproval;
import com.ganesh.repository.LoanApprovalRepository;

@Service
public class LoanApprovalService {
	
	@Autowired
	private LoanApprovalRepository loanApprovalRepository;
	
	@Autowired
	private RestTemplate rt;
	
	@Value("${minimum.credit.score}")
	private int minCreditScore;
	
	String loanApplication_URL="http://localhost:8083/loans/";
	public LoanApproval processLoanApproval(LoanApproval loanApproval) {
		if(loanApproval.getCreditScore() >= minCreditScore) {
			loanApproval.setIsApproved(true);
			loanApproval.setRejectionReason(null);
		}
		else {
			loanApproval.setIsApproved(false);
			loanApproval.setRejectionReason("Your Credit Score is Low");
		}
		
		LoanApplicationDTO loanApplicationDTO= rt.getForObject(loanApplication_URL+loanApproval.getLoanApplicationId(), LoanApplicationDTO.class);
		
		loanApproval.setCustomerId(loanApplicationDTO.getCustomerId());
		loanApproval.setLoanAmount(loanApplicationDTO.getLoanAmount());
		loanApproval.setLoanType(loanApplicationDTO.getLoanType());
		
		if(loanApproval.getIsApproved()) {
			loanApplicationDTO.setStatus("Processed and Approved");
		}
		else {
			loanApplicationDTO.setStatus("Processed and Rejected: Due to Low Credit Score");
		}
		rt.put(loanApplication_URL+"/update/"+loanApplicationDTO.getStatus()+"/"+loanApproval.getLoanApplicationId(), loanApplicationDTO);
		
		
		return loanApprovalRepository.save(loanApproval);
	}
	public List<LoanApproval> getAllLoanApprovals(){
		return loanApprovalRepository.findAll();
	}
	
	public LoanApproval getLoanApprovalById(Long id) {
		return loanApprovalRepository.findById(id).orElseThrow(() -> new RuntimeException("Loan Approval not found"));
	}
	
	
	
}
