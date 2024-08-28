package com.horsepower.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.horsepower.order.bo.OrderBO;
import com.horsepower.order.entity.OrderEntity;
import com.horsepower.order.entity.ProductOrder;
import com.horsepower.product.bo.ProductBO;
import com.horsepower.product.domain.ProductInfo;
import com.horsepower.user.bo.UserBO;
import com.horsepower.user.entity.UserEntity;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/horsepower/admin")
@Controller
public class AdminController {
	
	@Autowired
	private UserBO userBO;
	
	@Autowired
	private ProductBO productBO;
	
	@Autowired
	private OrderBO orderBO;

	@GetMapping("/manage-user")
	public String manageUser(HttpSession session,
			Model model,
			@RequestParam(value = "prevId", required = false) Integer prevIdParam,
			@RequestParam(value = "nextId", required = false) Integer nextIdParam) {
		List<UserEntity> usersList = userBO.getUserEntityList(prevIdParam, nextIdParam);
		int prevId = 0;
		int nextId = 0;
		if (usersList.isEmpty() == false) { 
			prevId = usersList.get(0).getId(); 
			nextId = usersList.get(usersList.size() - 1).getId(); 
			
			if (userBO.isPrevLastPageByUserId(prevId)) {
				prevId=0;
			}
			
			if (userBO.isNextLastPageByUserId(nextId)) {
				nextId=0;
			}
		}
		model.addAttribute("prevId", prevId);
		model.addAttribute("nextId", nextId);
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
			Model model,
			@RequestParam(value = "prevId", required = false) Integer prevIdParam,
			@RequestParam(value = "nextId", required = false) Integer nextIdParam) {
		
		List<ProductInfo> productInfoList = productBO.getProductInfo(prevIdParam, nextIdParam);
		int prevId = 0;
		int nextId = 0;
		if (productInfoList.isEmpty() == false) { // 글목록이 비어있지 않을때
			prevId = productInfoList.get(0).getProduct().getId(); // 첫번째 칸 id
			nextId = productInfoList.get(productInfoList.size() - 1).getProduct().getId(); // 마지막 칸 id
			
			if (productBO.isPrevLastPageByUserId(prevId)) {
				prevId=0;
			}
			
			if (productBO.isNextLastPageByUserId(nextId)) {
				nextId=0;
			}
		}
		model.addAttribute("prevId", prevId);
		model.addAttribute("nextId", nextId);
		model.addAttribute("productInfoList", productInfoList);
		
		String authority = (String)session.getAttribute("userAuthority");
		
		if (authority.contains("Admin")) {
			return "admin/manage-product";
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
	
	@GetMapping("/manage-product-edit")
	public String manageProdcutEdit(
			@RequestParam("productId") int productId,
			HttpSession session,
			Model model) {
		ProductInfo productInfo = productBO.getProductInfoByProductId(productId);
		
		model.addAttribute("productInfo", productInfo);

		String authority = (String)session.getAttribute("userAuthority");
		
		if (authority.contains("Admin")) {
			return "admin/manage-product-edit";
		} else {
			return "redirect:/horsepower/product/product-list";
		}
	}
	
	@GetMapping("/order-status")
	public String orderStatus(HttpSession session,
			Model model,
			@RequestParam(value = "prevId", required = false) Integer prevIdParam,
			@RequestParam(value = "nextId", required = false) Integer nextIdParam) {
		
		List<OrderEntity> orderList = orderBO.getOrderStatusList(prevIdParam, nextIdParam);
		int prevId = 0;
		int nextId = 0;
		if (orderList.isEmpty() == false) { 
			prevId = orderList.get(0).getId(); 
			nextId = orderList.get(orderList.size() - 1).getId(); 
			
			if (orderBO.isPrevLastPageById(prevId)) {
				prevId=0;
			}
			
			if (orderBO.isNextLastPageById(nextId)) {
				nextId=0;
			}
		}
		
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
		
		String authority = (String)session.getAttribute("userAuthority");
		
		if (authority.contains("Admin")) {
			return "admin/order-status";
		} else {
			return "redirect:/horsepower/product/product-list";
		}
		
	}
	
	@GetMapping("/order-status-detail")
	public String orderStatusDetail(@RequestParam("orderId") int orderId, 
			HttpSession session, 
			Model model) {

		OrderEntity order = orderBO.getOrderEntityById(orderId);

		int productId = order.getProductId();
		int productDetailId = order.getProductDetailId();
		ProductInfo productInfo = productBO.getProductInfoByProductIdAndProductDetailId(productId, productDetailId);

		ProductOrder productOrder = new ProductOrder();
		productOrder.setOrder(order);
		productOrder.setProductInfo(productInfo);

		model.addAttribute("productOrder", productOrder);

		String authority = (String) session.getAttribute("userAuthority");

		if (authority.contains("Admin")) {
			return "admin/order-status-detail";
		} else {
			return "redirect:/horsepower/product/product-list";
		}
	}
}
