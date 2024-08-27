package com.horsepower.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.horsepower.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

	public UserEntity findByEmail(String email);
	
	public UserEntity findByEmailAndPassword(String email, String password);

	
	@Query(value = "select * from user "
			   + "where id > :standardId "
			   + "order by id DESC "
			   + "limit :listMaxSize",
			   nativeQuery = true)
	public List<UserEntity> findUserIdGreaterOrderByIdDESC(
		@Param("standardId") Integer standardId,
		@Param("listMaxSize") int listMaxSize);

	@Query(value = "select * from user "
			   + "where id < :standardId "
			   + "order by id ASC "
			   + "limit :listMaxSize",
			   nativeQuery = true)
	public List<UserEntity> findUserIdLessOrderByIdASC(
		@Param("standardId") Integer standardId,
		@Param("listMaxSize") int listMaxSize);
	
	@Query(value = "select * from user "
			+ "order by id desc " 
			+ "limit :listMaxSize", 
			nativeQuery = true)
	public List<UserEntity> findUserOrderByIdDESCLimit(@Param("listMaxSize") int listMaxSize);
	
	@Query(value = "select * from user "
			   + "order by id DESC",
			   nativeQuery = true)
	public List<UserEntity> findUserOrderByIdDESC();

	@Query(value = "select * from user "
			   + "order by id ASC",
			   nativeQuery = true)
	public List<UserEntity> findUserOrderByIdASC();
}
