package com.horsepower.chekcout;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.horsepower.chekcout.bo.CheckoutBO;
import com.horsepower.chekcout.entity.CheckoutEntity;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/horsepower/checkout")
@RestController
public class CheckoutController {

	@Autowired
	private CheckoutBO checkoutBO;
	
	@GetMapping("/info")
	public String checkout(Model model,
			HttpSession session) {
		
		String UserEmail = (String) session.getAttribute("userEmail");
		
		List<CheckoutEntity> checkoutList = checkoutBO.getCheckoutListByUserEmail(UserEmail);
		
		model.addAttribute("checkoutList", checkoutList);
		
		return "fragments/header";
	}
}
