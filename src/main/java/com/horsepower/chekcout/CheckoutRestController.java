package com.horsepower.chekcout;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.horsepower.chekcout.bo.CheckoutBO;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/horsepower/checkout")
@RestController
public class CheckoutRestController {
	
	@Autowired
	private CheckoutBO checkoutBO;

	@PostMapping("/add")
	public Map<String, Object> add(
			@RequestParam("productId") int productId,
			@RequestParam("quantity") int quantity,
			@RequestParam("color") String color,
			@RequestParam("size") String size,
			HttpSession session) {
		
		String userEmail = (String) session.getAttribute("userEmail");
		
		checkoutBO.addCheckOutByProductIdAndUserId(productId, quantity, color, size, userEmail);
		
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		result.put("result", "success");
		
		return result;
	}
}
