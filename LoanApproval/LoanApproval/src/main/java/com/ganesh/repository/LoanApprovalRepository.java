package com.ganesh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ganesh.entity.LoanApproval;
@Repository
public interface LoanApprovalRepository extends JpaRepository<LoanApproval, Long> {
	
	
	
}
