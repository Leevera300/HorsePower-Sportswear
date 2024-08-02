package com.horsepower.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/horsepower/user")
@Controller
public class UserController {

	@GetMapping("/sign-in")
	public String signIn() {
		return "user/sign-in";
	}
	
	@GetMapping("/sign-up")
	public String signUp() {
		return "user/sign-up";
	}
	
	@RequestMapping("/sign-out")
	public String signOut(HttpSession session) {
		session.removeAttribute("userId");
		session.removeAttribute("userEmail");
		session.removeAttribute("userFirstName");
		session.removeAttribute("userAuthority");
		
		return "redirect:/horsepower/user/sign-in";
	}
}
