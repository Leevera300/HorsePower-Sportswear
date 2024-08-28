package com.horsepower.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.horsepower.order.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {

	public OrderEntity findByOrderNumber(String orderNumber);

	public List<OrderEntity> findByUserId(int userId);

	@Query(value = "select * from `order` "
			   + "order by id DESC",
			   nativeQuery = true)
	public List<OrderEntity> findOrderOrderByIdDESC();

	@Query(value = "select * from `order` "
			   + "order by id ASC",
			   nativeQuery = true)
	public List<OrderEntity> findOrderOrderByIdASC();
	
	@Query(value = "select * from `order` "
			   + "where id > :standardId "
			   + "order by id DESC "
			   + "limit :listMaxSize",
			   nativeQuery = true)
	public List<OrderEntity> findOrderIdGreaterOrderByIdDESC(
			@Param("standardId") Integer standardId,
			@Param("listMaxSize") int listMaxSize);

	@Query(value = "select * from `order` "
			   + "where id < :standardId "
			   + "order by id ASC "
			   + "limit :listMaxSize",
			   nativeQuery = true)
	public List<OrderEntity> findOrderIdLessOrderByIdASC(
		@Param("standardId") Integer standardId,
		@Param("listMaxSize") int listMaxSize);
	
	@Query(value = "select * from `order` "
			+ "order by id desc " 
			+ "limit :listMaxSize", 
			nativeQuery = true)
	public List<OrderEntity> findOrderOrderByIdDESCLimit(@Param("listMaxSize") int listMaxSize);
}
