package com.horsepower.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.horsepower.user.bo.UserBO;
import com.horsepower.user.entity.UserEntity;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/horsepower/admin")
@Controller
public class AdminController {
	
	@Autowired
	private UserBO userBO;

	@GetMapping("/manage-user")
	public String manageUser(HttpSession session,
			Model model) {
		List<UserEntity> usersList = userBO.getUserEntityList();
		
		model.addAttribute("usersList", usersList);
		
		String authority = (String)session.getAttribute("userAuthority");
		
		if (authority.contains("Admin")) {
			return "admin/manage-user";
		} else {
			return "redirect:/horsepower/product/product-list";
		}
		
	}
	
	@GetMapping("/manage-user-edit")
	public String manageUserEdit(
			@RequestParam("userId") int userId,
			HttpSession session,
			Model model) {
		
		UserEntity user = userBO.getUserEntityById(userId);
		
		model.addAttribute("user", user);
		
		String authority = (String)session.getAttribute("userAuthority");
		
		model.addAttribute("user", user);
		
		if (authority.contains("Admin")) {
			return "admin/manage-user-edit";
		} else {
			return "redirect:/horsepower/product/product-list";
		}
	}
	
	@GetMapping("/manage-product")
	public String manageProduct(HttpSession session,
			Model model) {
		
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
