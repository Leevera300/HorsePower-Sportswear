package com.horsepower.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.horsepower.order.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {

	public OrderEntity findByOrderNumber(String orderNumber);

	public List<OrderEntity> findByUserId(int userId);

}
