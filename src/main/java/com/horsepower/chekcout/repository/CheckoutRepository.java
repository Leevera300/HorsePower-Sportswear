package com.horsepower.chekcout.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.horsepower.chekcout.entity.CheckoutEntity;

public interface CheckoutRepository extends JpaRepository<CheckoutEntity, Integer> {

	public List<CheckoutEntity> findByUserEmail(String userEmail);
}
