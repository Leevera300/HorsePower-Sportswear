package com.horsepower.product;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/horsepower/product")
@Controller
public class ProdutController {

	@GetMapping("/product-list")
	public String productList() {
		return "product/product-list";
	}
}
