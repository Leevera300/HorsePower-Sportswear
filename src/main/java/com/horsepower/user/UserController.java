package com.horsepower.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.horsepower.order.bo.OrderBO;
import com.horsepower.order.entity.OrderEntity;
import com.horsepower.order.entity.ProductOrder;
import com.horsepower.product.bo.ProductBO;
import com.horsepower.product.domain.ProductInfo;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/horsepower/user")
@Controller
public class UserController {
	
	@Autowired
	private OrderBO orderBO;
	
	@Autowired
	private ProductBO productBO;

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
	
	@GetMapping("/user-order-status")
	public String userOrderStatus(HttpSession session,
			Model model) {
		int userId = (int) session.getAttribute("userId");
		
		// get order status list by user id
		List<OrderEntity> orderList = orderBO.getOrderStatusListByUserId(userId);
		
		int productId;
		int productDetailId;
		ProductInfo productInfo = new ProductInfo();
		List<ProductOrder> productOrders = new ArrayList<>();
		for (OrderEntity order : orderList) {
			ProductOrder productOrder = new ProductOrder();
			productOrder.setOrder(order);
			
			productId = order.getProductId();
			productDetailId = order.getProductDetailId();
			
			productInfo = productBO.getProductInfoByProductIdAndProductDetailId(productId, productDetailId);
			productOrder.setProductInfo(productInfo);
			
			productOrders.add(productOrder);
		}
		
		
		
		model.addAttribute("productOrders", productOrders);
		
		return "user/user-order-status";
	}
}
