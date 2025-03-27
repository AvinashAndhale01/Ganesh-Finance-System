package com.finance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finance.entity.ReportServiceEntity;
import com.finance.service.ReportService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/reports")
public class ReportController {
	
	@Autowired
	private ReportService reportService;
	
	@PostMapping
	public ReportServiceEntity createReport(@Valid @RequestBody ReportServiceEntity report) {
		return reportService.generateReport(report);
	}
	
	@GetMapping
	public List<ReportServiceEntity> getAllReports(){
		return reportService.getAllReports();
	}
	
	@GetMapping("/{id}")
	public ReportServiceEntity getReportById(@PathVariable Long id) {
		return reportService.getReportById(id);
	}
	
}
