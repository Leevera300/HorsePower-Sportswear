package com.horsepower.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.horsepower.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

}
