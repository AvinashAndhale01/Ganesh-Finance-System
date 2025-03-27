package com.ganesh.service;

import lombok.Data;

@Data
public class NotificationsDTO {
	 
	 private Long loanApplicationId;
	 
	 private String loanStatus;
	 
	 private String email;
}
