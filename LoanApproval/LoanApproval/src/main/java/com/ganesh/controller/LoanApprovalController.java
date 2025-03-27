package com.ganesh.controller;

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

import com.ganesh.entity.LoanApproval;
import com.ganesh.service.LoanApprovalService;

@RestController
@RequestMapping("/loan-approval")
public class LoanApprovalController {
	
	@Autowired
	private LoanApprovalService loanApprovalService;
	
	@PostMapping
	public ResponseEntity<String> processLoanApproval(@RequestBody LoanApproval loanApproval) {
		loanApprovalService.processLoanApproval(loanApproval);
		return new ResponseEntity<>("Loan is successfully reviewed with id: "+loanApproval.getId(), HttpStatus.CREATED);
	}
	
	@GetMapping()
	public List<LoanApproval> getAllLoanApprovals(){
		return loanApprovalService.getAllLoanApprovals();
	}
	
	@GetMapping("/{id}")
	public LoanApproval getLoanApprovalById(@PathVariable Long id) {
		return loanApprovalService.getLoanApprovalById(id);
	}
	
	
		

}
