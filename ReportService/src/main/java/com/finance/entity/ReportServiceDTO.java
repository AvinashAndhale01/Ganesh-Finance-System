package com.finance.entity;

import lombok.Data;

@Data
public class ReportServiceDTO {
	
	private Long id;
	private String customerName;
	private Long loanId;
	private Double loanAmount;
	private String loanStatus;
}
