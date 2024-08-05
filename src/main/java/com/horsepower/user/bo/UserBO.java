package com.horsepower.user.bo;

import java.util.List;

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
				.authority("user")
				.build());
	}
	
	public List<UserEntity> getUserEntityList() {
		return userRepository.findAll();
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
	
}
