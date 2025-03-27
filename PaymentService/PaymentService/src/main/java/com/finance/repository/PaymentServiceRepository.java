package com.finance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finance.entity.Payment;

@Repository
public interface PaymentServiceRepository extends JpaRepository<Payment, Long> {

	public List<Payment> findByLoanId(Long loanId);
}
