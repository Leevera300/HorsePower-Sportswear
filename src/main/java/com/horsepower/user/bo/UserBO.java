package com.horsepower.user.bo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.horsepower.user.entity.UserEntity;
import com.horsepower.user.repository.UserRepository;

@Service
public class UserBO {

	@Autowired
	private UserRepository userRepository;
	
	private static final int LIST_MAX_SIZE = 10;
	
	// input: params
	// output: UserEntity
	
	public UserEntity addUser(String firstName, String lastName, String dob, String email, String password) {
		return userRepository.save(UserEntity.builder()
				.firstName(firstName)
				.lastName(lastName)
				.dob(dob)
				.email(email)
				.password(password)
				.authority("user")
				.build());
	}
	
	public List<UserEntity> getUserEntityList(Integer prevId, Integer nextId) {
		
		List<UserEntity> userList = new ArrayList<>();
		Integer standardId = null; // 기준 postId;
		if (prevId != null) { // 2) 이전
			standardId = prevId;
			userList = userRepository.findUserIdLessOrderByIdASC(standardId, LIST_MAX_SIZE);
			Collections.reverse(userList); // void = 뒤집고 저장까지 해준다
		} else if (nextId != null) { // 1) 다음
			standardId = nextId;
			userList = userRepository.findUserIdGreaterOrderByIdDESC(standardId, LIST_MAX_SIZE);
		} else {
			userList = userRepository.findUserOrderByIdDESCLimit(LIST_MAX_SIZE);
		}
		
		return userList;
	}
	
	public UserEntity getUserEntityById(int Id) {
		return userRepository.findById(Id).orElse(null);
	}
	
	public UserEntity getUserEntityByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	public UserEntity getUserEntityByEmailAndPassword(String email, String password) {
		return userRepository.findByEmailAndPassword(email, password);
	}
	
	public void updateUserById(int Id, String firstName, String lastName, String dob, String email, String authority) {
		UserEntity user = userRepository.findById(Id).orElse(null);
		
		UserEntity updatedUser = UserEntity.builder()
				.id(Id)
				.firstName(firstName)
				.lastName(lastName)
				.dob(dob)
				.email(email)
				.password(user.getPassword())
				.authority(authority)
				.createdAt(user.getCreatedAt())
				.build();
		
		userRepository.save(updatedUser);

	}
	
	public void deleteUserById(int Id) {
		userRepository.deleteById(Id);
	}

	public boolean isPrevLastPageByUserId(int prevId) {
		int maxPostId = userRepository.findUserOrderByIdDESC().get(0).getId();
		return maxPostId == prevId;
	}

	public boolean isNextLastPageByUserId(int nextId) {
		int minPostId = userRepository.findUserOrderByIdASC().get(0).getId();
		return minPostId == nextId;
	}
	
}
