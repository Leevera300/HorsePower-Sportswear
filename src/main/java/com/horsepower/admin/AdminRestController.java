package com.horsepower.admin;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.horsepower.user.bo.UserBO;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/horsepower/admin")
@RestController
public class AdminRestController {

	@Autowired
	private UserBO userBO;
	
	@PutMapping("/manage-user-update")
	public Map<String, Object> manageUserUpdate(
			@RequestParam("userId") int userId,
			@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName,
			@RequestParam("dob") String dob,
			@RequestParam("email") String email,
			@RequestParam("authority") String UserAuthority,
			HttpSession session) {
		
		String authority = (String)session.getAttribute("userAuthority");
		
		Map<String, Object> result = new HashMap<>();
		if (authority.contains("Admin") == false) {
			result.put("code", 403);
			result.put("error_message", "Please log in with Admin account.");
			return result;
		}
		
		userBO.updateUserById(userId, firstName, lastName, dob, email, authority);
		
		result.put("code", 200);
		result.put("result", "success");
		
		return result;
	}
	
	@DeleteMapping("/manage-user-delete")
	public Map<String, Object> manageUserDelete(
			@RequestParam("userId") int userId,
			HttpSession session) {
		
		String authority = (String)session.getAttribute("userAuthority");
		
		Map<String, Object> result = new HashMap<>();
		if (authority.contains("Admin") == false) {
			result.put("code", 403);
			result.put("error_message", "Please log in with Admin account.");
			return result;
		}
		
		userBO.deleteUserById(userId);
		
		result.put("code", 200);
		result.put("result", "success");
		
		return result;
	}
}
