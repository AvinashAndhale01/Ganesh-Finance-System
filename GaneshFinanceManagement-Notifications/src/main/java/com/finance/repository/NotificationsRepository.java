package com.finance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finance.entity.Notifications;



@Repository
public interface NotificationsRepository extends JpaRepository<Notifications, Long> {
	
}