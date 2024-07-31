package com.horsepower.user.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.horsepower.user.entity.UserEntity;
import com.horsepower.user.repository.UserRepository;

@Service
public class UserBO {

	@Autowired
	private UserRepository userRepository;
	
	// input: params
	// output: UserEntity
	
	public UserEntity addUser(String firstName, String lastName, String dob, String email, String password) {
		return userRepository.save(UserEntity.builder()
				.firstName(firstName)
				.lastName(lastName)
				.dob(dob)
				.email(email)
				.password(password)
				.build());
	}
	
}
