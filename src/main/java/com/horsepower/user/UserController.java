package com.horsepower.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/horsepower/user")
@Controller
public class UserController {

	@GetMapping("/sign-in-up")
	public String signInUp() {
		return "user/sign-in-up";
	}
	
	@RequestMapping("/sign-out")
	public String signOut(HttpSession session) {
		session.removeAttribute("userId");
		session.removeAttribute("userEmail");
		session.removeAttribute("userFirstName");
		
		return "redirect:/horsepower/user/sign-in-up";
	}
}
