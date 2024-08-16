package com.horsepower.chekcout.entity;

import com.horsepower.product.domain.Product;
import com.horsepower.product.domain.ProductDetail;
import com.horsepower.product.domain.ProductPics;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CheckoutInfo {

	private CheckoutEntity checkout;
	
	private Product product;
	
	private ProductDetail productDetail;
	
	private ProductPics productPic;
}
