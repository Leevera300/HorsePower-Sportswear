package com.horsepower.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/horsepower/user")
@Controller
public class UserController {

	@GetMapping("/sign-in-up")
	public String signInUp() {
		return "user/sign-in-up";
	}
}
