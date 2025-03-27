package com.finance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.finance.entity.LoanApplication;
import com.finance.repository.LoanApplicationRepository;

@Service
 public class LoanApplicationServiceImpl implements LoanApplicationService{
	
	@Autowired
	private LoanApplicationRepository loanAppRepo;
	
	@Autowired
	private RestTemplate rT;
	
	private static String Customer_url="http://identity-service/auth/user/";
	
	public LoanApplication createApplication(LoanApplication application) {
		UserLoginDTO userLoginDTO = rT.getForObject(Customer_url+application.getCustomerId(), UserLoginDTO.class);
		application.setEmail(userLoginDTO.getEmail());
		application.getCustomerId();
//		application.setPhone(customerDTO.getPhone());
		return loanAppRepo.save(application);
	}
	
	public List<LoanApplication> getAllLoanApplication() {
		return loanAppRepo.findAll();
	}

	public LoanApplication getLoanApplicationById(Long id) {
		return loanAppRepo.findById(id).orElseThrow(()->new RuntimeException("Application not found"));
	}
	
	public void updateStatus(String status, Long applicationId) {
		LoanApplication application= loanAppRepo.findById(applicationId).orElseThrow(()->new RuntimeException("Application not found, Please check Application id"));
		application.setStatus(status);
		
		loanAppRepo.save(application);
	}
	
	public List<LoanApplication> getAllByCustomerId(Long customerId){
		return loanAppRepo.findAllByCustomerId(customerId);
	}
	
	public String deleteById(Long id) {
		loanAppRepo.deleteById(id);
		return "application deleted";
	}

}
