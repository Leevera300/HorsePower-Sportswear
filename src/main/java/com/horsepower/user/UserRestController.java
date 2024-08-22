package com.horsepower.user;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.horsepower.common.PasswordHash;
import com.horsepower.user.bo.UserBO;
import com.horsepower.user.entity.UserEntity;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/horsepower/user")
@RestController
public class UserRestController {
    
	@Autowired
	private UserBO userBO;

	@PostMapping("/sign-up")
	public Map<String, Object> signUp(
			@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName,
			@RequestParam("dob") String dob,
			@RequestParam("email") String email,
			@RequestParam("password") String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
		
		// hashed password TODO
		String hashedPassword = PasswordHash.createHash(password);
		
		// DB insert
		UserEntity user = userBO.addUser(firstName, lastName, dob, email, hashedPassword);
		
		// 응답값
		Map<String, Object> result = new HashMap<>();
		if (user != null) {
			result.put("code", 200);
			result.put("result", "성공");
		} else {
			result.put("code", 500);
			result.put("error_message", "회원가입에 실패했습니다.");
		}
		return result;
	}
	
	@PostMapping("/is-duplicated-email")
	public Map<String, Object> isDuplicatedEmail(
			@RequestParam("email") String email) {
		
		UserEntity user = userBO.getUserEntityByEmail(email);
		
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		if (user != null) {
			result.put("is_duplicated_email", true);
		} else {
			result.put("is_duplicated_email", false);
		}
		return result;
	}
	
	@PostMapping("/sign-in")
	public Map<String, Object> signIn(
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			HttpSession session) throws NoSuchAlgorithmException, InvalidKeySpecException {
		
		
		UserEntity user = userBO.getUserEntityByEmail(email);
		
		// hashing password TODO
		Boolean hashedPassword = PasswordHash.validatePassword(password, user.getPassword());
				
		Map<String, Object> result = new HashMap<>();
		if (user != null && hashedPassword == true) { // 성공
			session.setAttribute("userId", user.getId());
			session.setAttribute("userEmail", user.getEmail());
			session.setAttribute("userFirstName", user.getFirstName());
			session.setAttribute("userAuthority", user.getAuthority());
				
			result.put("code", 200);
			result.put("result", "success");
		} else {
			result.put("code", 403);
			result.put("error_message", "Please check your email or password.");
		}
	
	return result;
	}
	
	
}
