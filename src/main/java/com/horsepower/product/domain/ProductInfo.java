package com.horsepower.product.domain;

import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ProductInfo {

	private Product product;
	
	private ProductDetail productDetail;
	
	private List<ProductPics> productPics;
}
