package com.finance.service;

import lombok.Data;

@Data
public class LoanApprovalDTO {
	private Long id;

    private Long loanApplicationId;
    
    private int creditScore;
    
    private Boolean isApproved;
    
    private String rejectionReason;
    
    private double loanAmount;
    
    private String loanType;
    
    private Long customerId;
}
