package com.horsepower.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/horsepower/admin")
@Controller
public class AdminController {

	@GetMapping("/manage-user")
	public String manageUser(HttpSession session) {
		String authority = (String)session.getAttribute("userAuthority");
		
		if (authority.contains("Admin")) {
			return "admin/manage-user";
		} else {
			return "redirect:/horsepower/product/product-list";
		}
		
	}
	
	@GetMapping("/manage-product")
	public String manageProduct(HttpSession session) {
		String authority = (String)session.getAttribute("userAuthority");
		
		if (authority.contains("Admin")) {
			return "admin/manage-product";
		} else {
			return "redirect:/horsepower/product/product-list";
		}
		
	}
	
	@GetMapping("/order-status")
	public String orderStatus(HttpSession session) {
String authority = (String)session.getAttribute("userAuthority");
		
		if (authority.contains("Admin")) {
			return "admin/order-status";
		} else {
			return "redirect:/horsepower/product/product-list";
		}
		
		
	}
	
	@GetMapping("/manage-product-create")
	public String manageProductCreate(HttpSession session) {
		String authority = (String)session.getAttribute("userAuthority");
		
		if (authority.contains("Admin")) {
			return "admin/manage-product-create";
		} else {
			return "redirect:/horsepower/product/product-list";
		}
		
	}
}
