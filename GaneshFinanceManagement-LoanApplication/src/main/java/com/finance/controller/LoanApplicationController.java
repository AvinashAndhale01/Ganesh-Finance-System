package com.finance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finance.entity.LoanApplication;
import com.finance.service.LoanApplicationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/loans")
public class LoanApplicationController {
	
	@Autowired
	private LoanApplicationService service;
	
	@PostMapping
	public ResponseEntity<String> createLoanApplication(@Valid @RequestBody LoanApplication application) {
		service.createApplication(application);		
		return new ResponseEntity<>("Your Application is submitted with Application id: " +application.getId(), HttpStatus.CREATED);
	}
	
	@GetMapping
	public List<LoanApplication> getAllLoanApplication(){
		return service.getAllLoanApplication();
	}
	
	@GetMapping("/{id}")
	public LoanApplication getLoanApplicationLoanById(@PathVariable Long id) {
		return service.getLoanApplicationById(id);
	}
	
	@PutMapping("/update/{status}/{id}")
	public ResponseEntity<String> updateStatus(@PathVariable String status, @PathVariable Long id){
		service.updateStatus(status, id);
		return new ResponseEntity<>("Status of Application id:"+id+"was updated Succesfully", HttpStatus.OK);
	}
	
	@GetMapping("/customer/{customerId}")
	public List<LoanApplication> getAllByCustomerId(@PathVariable Long customerId) {
		return service.getAllByCustomerId(customerId);
	}
	
	
}
