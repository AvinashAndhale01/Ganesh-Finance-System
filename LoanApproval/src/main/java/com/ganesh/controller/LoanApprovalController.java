package com.ganesh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public LoanApproval processLoanApproval(@RequestBody LoanApproval loanApproval) {
		return loanApprovalService.processLoanApproval(loanApproval);
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
