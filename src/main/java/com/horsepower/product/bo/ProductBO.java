package com.horsepower.product.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.horsepower.product.domain.Product;
import com.horsepower.product.mapper.ProductMapper;

@Service
public class ProductBO {

	@Autowired
	private ProductDetailBO prodcutDetailBO;
	
	@Autowired
	private ProductPicsBO productPicsBO;
	
	@Autowired
	private ProductMapper productMapper;
	
	public void addProduct(String productName, String category,  String productDesc, MultipartFile imgFile1,
			 String color, String size, int quantity, double price, Integer sale) {
		
		int productId = productMapper.insertProdcut(productName, category, productDesc);
		
		prodcutDetailBO.addProductDetail(productId, color, size, quantity, price, sale);
		
		productPicsBO.addProdcutPics(productId, imgFile1);
	}
	
}
