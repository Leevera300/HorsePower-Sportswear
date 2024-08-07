package com.horsepower.product.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.horsepower.product.domain.Product;
import com.horsepower.product.domain.ProductDetail;
import com.horsepower.product.domain.ProductInfo;
import com.horsepower.product.domain.ProductPics;
import com.horsepower.product.mapper.ProductMapper;

@Service
public class ProductBO {

	@Autowired
	private ProductDetailBO productDetailBO;
	
	@Autowired
	private ProductPicsBO productPicsBO;
	
	@Autowired
	private ProductMapper productMapper;
	
	@SuppressWarnings("null")
	@Transactional
	public void addProduct(String name, String category,  String description, MultipartFile imgFile1,
			 String color, String size, int quantity, double price, Integer sale) {
		
		Product product = new Product();
		product.setName(name);
		product.setCategory(category);
		product.setDescription(description);
		productMapper.insertProduct(product);
		
		productDetailBO.addProductDetail(product.getId(), color, size, quantity, price, sale);
		
		productPicsBO.addProdcutPics(product.getId(), imgFile1);
	}
	
	public List<ProductInfo> getProductInfo() {
		List<ProductInfo> productInfoList = new ArrayList<>();
		
		List<Product> productList = productMapper.selectProduct();

		for (Product product : productList) {
			ProductInfo productInfo = new ProductInfo();
			
			productInfo.setProduct(product);
			
			ProductDetail productDetail = productDetailBO.getProductDetailByProductId(product.getId());
			productInfo.setProductDetail(productDetail);
			
			List<ProductPics> productPics = productPicsBO.getProductPicsByProductId(product.getId());
			productInfo.setProductPics(productPics);
			
			productInfoList.add(productInfo);
		}
		
		
		return productInfoList;
	}
	
	public ProductInfo getProductInfoByProductId(int productId) {
		ProductInfo productInfo = new ProductInfo();
		
		Product product = productMapper.selectProductById(productId);
		productInfo.setProduct(product);
		
		ProductDetail productDetail = productDetailBO.getProductDetailByProductId(productId);
		productInfo.setProductDetail(productDetail);
		
		List<ProductPics> productPics = productPicsBO.getProductPicsByProductId(productId);
		productInfo.setProductPics(productPics);
		
		return productInfo;
	}
}
