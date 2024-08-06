package com.horsepower.product.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.horsepower.product.mapper.ProductDetailMapper;

@Service
public class ProductDetailBO {
	
	@Autowired
	private ProductDetailMapper productDetailMapper;

	public void addProductDetail(int productId, String color, String size, int quantity, 
			double price, Integer sale) {
		
		productDetailMapper.insertProdcutDetail(productId, color, size, quantity, price, sale);
	}
}
