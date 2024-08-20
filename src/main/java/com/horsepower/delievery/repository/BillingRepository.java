package com.horsepower.delievery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.horsepower.delievery.entity.BillingEntity;

public interface BillingRepository extends JpaRepository<BillingEntity, Integer>{

}
