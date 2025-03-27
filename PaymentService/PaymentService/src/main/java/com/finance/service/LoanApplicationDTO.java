package com.finance.service;

import lombok.Data;

@Data
public class LoanApplicationDTO {
	

    private Long id;

    private Long customerId;

    private String loanType;

    private double loanAmount;

    private String status = "Pending";

    private String email;

    private String phone;
    

}
