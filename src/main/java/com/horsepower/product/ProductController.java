package com.horsepower.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.horsepower.AOP.TimeTrace;
import com.horsepower.product.bo.ProductBO;
import com.horsepower.product.domain.ProductInfo;

@RequestMapping("/horsepower/product")
@Controller
public class ProductController {

	@Autowired
	private ProductBO productBO;
	
	@TimeTrace
	@GetMapping("/product-list")
	public String productList(Model model) {
		
		List<ProductInfo> newProductInfoList = productBO.getProductInfoOrderByCreatedAtDESC();
		
		List<ProductInfo> hotProductInfoList = productBO.getProductInfoOrderByUpdatedAt();
		
		model.addAttribute("newProductInfoList", newProductInfoList);
		model.addAttribute("hotProductInfoList", hotProductInfoList);
		
		return "product/product-list";
	}
	
	@GetMapping("/product-detail")
	public String productList(
			@RequestParam("productId") int productId,
			Model model) {
		
		ProductInfo productInfo = productBO.getProductInfoByProductId(productId);
		
		model.addAttribute("productInfo", productInfo);
		
		return "product/product-detail";
	}
}
