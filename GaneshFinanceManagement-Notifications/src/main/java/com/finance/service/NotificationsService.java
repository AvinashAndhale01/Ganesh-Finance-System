package com.finance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.finance.entity.Notifications;
import com.finance.repository.NotificationsRepository;

@Service
public class NotificationsService {

	@Autowired
	private RestTemplate rt;
	
	@Autowired
	private JavaMailSender emailSender;
	
	
	@Autowired
	private NotificationsRepository repo;
	
	 @Value("${spring.mail.username}")
	 private String fromEmail;
	 
	 private String loanApplication_URL="http://ganeshfinancemanagement-loanapplication/loans/";
	 
	 
	 public Notifications sendLoanStatus(Notifications notification) {
		 LoanApplicationDTO loanApplicationDTO =rt.getForObject(loanApplication_URL+notification.getLoanApplicationId(), LoanApplicationDTO.class);
		 
		 notification.setEmail(loanApplicationDTO.getEmail());
		 
		 String subject = "Loan Application Status Update";
         String message = "Dear Customer,\n\nYour loan application status is: " + loanApplicationDTO.getStatus() + "\n\nThank you for choosing us.";
         
         sendEmail(notification.getEmail(), subject, message);
         
         return repo.save(notification);
	 }
	 
	 public void sendEmail(String to, String subject, String message) {
		 SimpleMailMessage email = new SimpleMailMessage();
		 email.setFrom(fromEmail);
	        email.setTo(to);
	        email.setSubject(subject);
	        email.setText(message);

	        emailSender.send(email);
	 }
}
