package com.finance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finance.entity.Notifications;
import com.finance.service.NotificationsService;

@RestController
@RequestMapping("/notification")
public class NotificationsController {

	@Autowired
	private NotificationsService service;
	
	@PostMapping
	public ResponseEntity<String> sendLoanStatusUpdate(@RequestBody Notifications notification) {
        service.sendLoanStatus(notification);
        return new ResponseEntity<>("Loan status update email sent successfully!", HttpStatus.OK);
    }
}
