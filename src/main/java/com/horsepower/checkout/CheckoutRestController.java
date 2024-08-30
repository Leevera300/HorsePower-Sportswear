package com.horsepower.checkout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.horsepower.chekcout.bo.CheckoutBO;
import com.horsepower.chekcout.entity.CheckoutEntity;
import com.horsepower.chekcout.entity.CheckoutInfo;
import com.horsepower.chekcout.entity.CheckoutUpdate;
import com.horsepower.order.bo.OrderBO;
import com.horsepower.order.entity.DelieveryInfo;
import com.horsepower.order.entity.OrderEntity;
import com.horsepower.product.bo.ProductBO;
import com.horsepower.product.bo.ProductDetailBO;
import com.horsepower.product.bo.ProductPicsBO;
import com.horsepower.product.domain.Product;
import com.horsepower.product.domain.ProductDetail;
import com.horsepower.product.domain.ProductPics;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/horsepower/checkout")
@RestController
public class CheckoutRestController {

	@Autowired
	private CheckoutBO checkoutBO;

	@Autowired
	private ProductBO productBO;

	@Autowired
	private ProductDetailBO productDetailBO;
	
	@Autowired
	private ProductPicsBO productPicBO;
	
	@Autowired OrderBO orderBO;

	@PostMapping("/add")
	public Map<String, Object> add(@RequestParam("productId") int productId, @RequestParam("quantity") int quantity,
			@RequestParam("color") String color, @RequestParam("size") String size, HttpSession session) {

		String userEmail = (String) session.getAttribute("userEmail");

		checkoutBO.addCheckOutByProductIdAndUserId(productId, quantity, color, size, userEmail);

		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		result.put("result", "success");

		return result;
	}

	@GetMapping("/info")
	public List<CheckoutInfo> info(HttpSession session) {
		String userEmail = (String) session.getAttribute("userEmail");

		List<CheckoutInfo> checkoutinfoList = new ArrayList<>();
		
		

		List<CheckoutEntity> checkoutList = checkoutBO.getCheckoutListByUserEmail(userEmail);

		for (CheckoutEntity checkout : checkoutList) {
			CheckoutInfo checkoutinfo = new CheckoutInfo();
			
			checkoutinfo.setCheckout(checkout);
			
			Product product = productBO.getProductById(checkout.getProductId());
			checkoutinfo.setProduct(product);
			
			ProductDetail productDetail = productDetailBO.getProductDetailById(checkout.getProductDetailId());
            checkoutinfo.setProductDetail(productDetail);
            
            ProductPics productPic = productPicBO.getProductPicByProductIdLimit1(product.getId());
            checkoutinfo.setProductPic(productPic);
            
            checkoutinfoList.add(checkoutinfo);
		}


		return checkoutinfoList;
	}
	
	@PostMapping("/delete")
	public Map<String, Object> delete(
			@RequestParam("checkoutId") int checkoutId) {

		checkoutBO.deleteCheckoutById(checkoutId);

		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		result.put("result", "success");

		return result;
	}
	
	@PutMapping("/update")
	public Map<String, Object> update(@ModelAttribute CheckoutUpdate checkoutUpdate) {

		List<Integer> checkoutIds = checkoutUpdate.getCheckoutIds();
		List<Integer> quantities = checkoutUpdate.getQuantities();
		
		checkoutBO.updateCheckoutByIdAndQuantity(checkoutIds, quantities);
		
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		result.put("result", "success");

		return result;
	}
	
	@PostMapping("/pay")
	public Map<String, Object> pay(@RequestBody DelieveryInfo delieveryInfo,
			HttpSession session) {
		
		Integer userId = (Integer) session.getAttribute("userId");
		String userEmail = (String) session.getAttribute("userEmail");
		
		List<OrderEntity> orderList = orderBO.addOrder(userId, userEmail, delieveryInfo);
		
		productDetailBO.updateProductDetailQuantity(orderList);
        
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		result.put("result", "success");

		return result;
	}
	

}
