package com.horsepower.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.horsepower.order.bo.OrderBO;
import com.horsepower.product.bo.ProductBO;
import com.horsepower.product.domain.ProductDetail;
import com.horsepower.product.domain.ProductDetailListWrapper;
import com.horsepower.user.bo.UserBO;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/horsepower/admin")
@RestController
public class AdminRestController {

	@Autowired
	private UserBO userBO;
	
	@Autowired
	private ProductBO productBO;
	
	@Autowired
	private OrderBO orderBO;
	
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
	
	@PostMapping("/product-manage-add")
	public Map<String, Object> prodcutManageAdd(
			@RequestParam("productName") String productName,
			@RequestParam("category") String category,
			@RequestParam("productDesc") String productDesc,
			@RequestParam("imgFile1") MultipartFile imgFile1,
			@ModelAttribute ProductDetailListWrapper productDetailsWrapper,
			HttpSession session) {
		
		String authority = (String)session.getAttribute("userAuthority");
		
		Map<String, Object> result = new HashMap<>();
		if (authority.contains("Admin") == false) {
			result.put("code", 403);
			result.put("error_message", "Please log in with Admin account.");
			return result;
		}
		
		List<ProductDetail> productDetail = productDetailsWrapper.getProductDetails();
		
		
		productBO.addProduct(productName, category, productDesc, imgFile1, productDetail);
		
		result.put("code", 200);
		result.put("result", "success");
		
		return result;
	}
	
	@GetMapping("/manage-product-delete")
	public Map<String, Object> prodcutManageDelete(
			@RequestParam("productId") int productId,
			HttpSession session) {
		
		String authority = (String)session.getAttribute("userAuthority");
		
		Map<String, Object> result = new HashMap<>();
		if (authority.contains("Admin") == false) {
			result.put("code", 403);
			result.put("error_message", "Please log in with Admin account.");
			return result;
		}
		
		productBO.deleteProductById(productId);
		
		result.put("code", 200);
		result.put("result", "success");
		
		return result;
	}
	
	@PutMapping("/product-manage-update")
	public Map<String, Object> prodcutManageUpdate(
			@RequestParam("productId") int productId,
			@RequestParam("productName") String productName,
			@RequestParam("category") String category,
			@RequestParam("productDesc") String productDesc,
			@RequestParam(value = "imgFile1", required = false) MultipartFile imgFile1,
			@ModelAttribute ProductDetailListWrapper productDetailsWrapper,
			HttpSession session) {
		
		String authority = (String)session.getAttribute("userAuthority");
		
		Map<String, Object> result = new HashMap<>();
		if (authority.contains("Admin") == false) {
			result.put("code", 403);
			result.put("error_message", "Please log in with Admin account.");
			return result;
		}
		
		List<ProductDetail> productDetail = productDetailsWrapper.getProductDetails();
		
		productBO.updateProductById(productId, productName, category, productDesc, imgFile1, productDetail);
		
		result.put("code", 200);
		result.put("result", "success");
		
		return result;
	}
	
	
		@DeleteMapping("/order-status-delete")
		public Map<String, Object> orderStatusDelete(
            @RequestParam("orderId") int orderId,
            HttpSession session) {
			
			String authority = (String)session.getAttribute("userAuthority");
			
			if (authority.contains("Admin") == false) {
				Map<String, Object> result = new HashMap<>();
				result.put("code", 403);
				result.put("error_message", "Please log in with Admin account.");
				return result;
			}
			
			orderBO.deleteOrderEntityById(orderId);
			
			Map<String, Object> result = new HashMap<>();
			result.put("code", 200);
			result.put("result", "success");
			
			return result;
		}
}
