package com.horsepower.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.horsepower.user.bo.UserBO;
import com.horsepower.user.entity.UserEntity;

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
			@RequestParam("password") String password) {
		
		// hashed password TODO
		
		// DB insert
		UserEntity user = userBO.addUser(firstName, lastName, dob, email, password);
		
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
}
