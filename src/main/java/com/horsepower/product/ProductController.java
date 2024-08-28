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
	public String productList(Model model,
			@RequestParam(value = "prevIndexNew", required = false) Integer prevIndexNewParam,
			@RequestParam(value = "nextIndexNew", required = false) Integer nextIndexNewParam,
			@RequestParam(value = "prevIndexHot", required = false) Integer prevIndexHotParam,
			@RequestParam(value = "nextIndexHot", required = false) Integer nextIndexHotParam) {
		
		int prevIndexNew = 0;
		int nextIndexNew = 0;
		int prevIndexHot = 0;
		int nextIndexHot = 0;
		
		
		List<ProductInfo> newProductInfoList = productBO.getProductInfoOrderByCreatedAtDESC(prevIndexNewParam, nextIndexNewParam);
		if (newProductInfoList.isEmpty() == false) {
			prevIndexNew = newProductInfoList.get(0).getPrevIndexNew();
			nextIndexNew = newProductInfoList.get(0).getNextIndexNew();
		}
		
		List<ProductInfo> hotProductInfoList = productBO.getProductInfoOrderByUpdatedAt(prevIndexHotParam, nextIndexHotParam);
		if (hotProductInfoList.isEmpty() == false) {
			prevIndexHot = hotProductInfoList.get(0).getPrevIndexNew();
			nextIndexHot = hotProductInfoList.get(0).getNextIndexNew();
		}
		
		model.addAttribute("prevIndexNew", prevIndexNew);
		model.addAttribute("nextIndexNew", nextIndexNew);
		model.addAttribute("prevIndexHot", prevIndexHot);
		model.addAttribute("nextIndexHot", nextIndexHot);
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
	
	@GetMapping("/product-list-category")
	public String productListByCategory(@RequestParam("category") String category, 
			Model model,
			@RequestParam(value = "prevIndex", required = false) Integer prevIndexParam,
			@RequestParam(value = "nextIndex", required = false) Integer nextIndexParam) {
		
		int prevIndex = 0;
		int nextIndex = 0;
		
		category = category.toUpperCase();
		

		List<ProductInfo> productInfoList = productBO.getProductInfoByCategory(category, prevIndexParam, nextIndexParam);
		if (productInfoList.isEmpty() == false) {
			prevIndex = productInfoList.get(0).getPrevIndexNew();
			nextIndex = productInfoList.get(0).getNextIndexNew();
		}

		model.addAttribute("productInfoList", productInfoList);
		model.addAttribute("category", category);
		model.addAttribute("prevIndex", prevIndex);
		model.addAttribute("nextIndex", nextIndex);

		return "product/product-list-category";
	}
}
