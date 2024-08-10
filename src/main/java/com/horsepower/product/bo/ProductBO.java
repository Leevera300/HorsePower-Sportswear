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
			
			List<ProductDetail> productDetailList = productDetailBO.getProductDetailListByProductId(product.getId());
			productInfo.setProductDetailList(productDetailList);
			
			List<ProductPics> productPics = productPicsBO.getProductPicsByProductId(product.getId());
			productInfo.setProductPics(productPics);
			
			productInfoList.add(productInfo);
		}
		
		
		return productInfoList;
	}
	
	public List<ProductInfo> getProductInfoOrderByCreatedAtDESC() {
		List<ProductInfo> newProductInfoList = new ArrayList<>();
		
		List<Product> newProductList = productMapper.selectProductOrderByCreatedAtDESC();

		for (Product product : newProductList) {
			ProductInfo productInfo = new ProductInfo();
			
			productInfo.setProduct(product);
			
			List<ProductDetail> productDetailList = productDetailBO.getProductDetailListByProductId(product.getId());
			productInfo.setProductDetailList(productDetailList);
			
			List<ProductPics> productPics = productPicsBO.getProductPicsByProductId(product.getId());
			productInfo.setProductPics(productPics);
			
			newProductInfoList.add(productInfo);
		}
		
		return newProductInfoList;
	}
	
	public List<ProductInfo> getProductInfoOrderByUpdatedAt() {
		List<ProductInfo> hotProductInfoList = new ArrayList<>();
		
		List<Product> hotProductList = productMapper.selectProductOrderByUpdatedAt();

		for (Product product : hotProductList) {
			ProductInfo productInfo = new ProductInfo();
			
			productInfo.setProduct(product);
			
			List<ProductDetail> productDetailList = productDetailBO.getProductDetailListByProductId(product.getId());
			productInfo.setProductDetailList(productDetailList);
			
			List<ProductPics> productPics = productPicsBO.getProductPicsByProductId(product.getId());
			productInfo.setProductPics(productPics);
			
			hotProductInfoList.add(productInfo);
		}
		
		return hotProductInfoList;
	}
	
	public ProductInfo getProductInfoByProductId(int productId) {
		ProductInfo productInfo = new ProductInfo();
		
		Product product = productMapper.selectProductById(productId);
		productInfo.setProduct(product);
		
		List<ProductDetail> productDetailList = productDetailBO.getProductDetailListByProductId(productId);
		productInfo.setProductDetailList(productDetailList);
		
		List<ProductPics> productPics = productPicsBO.getProductPicsByProductId(productId);
		productInfo.setProductPics(productPics);
		
		return productInfo;
	}
	
	public void deleteProductById(int id) {
		
		Product product = productMapper.selectProductById(id);
		
		productMapper.deleteProductById(id);
		
		productDetailBO.deleteProductDetailByProductId(product.getId());
		
		productPicsBO.deleteProductPicsByProductId(product.getId());
	}
	
	@Transactional
	public void updateProductById(int id, String name, String category,  String description, MultipartFile imgFile1,
			 String color, String size, int quantity, double price, Integer sale) {
		
		Product product = productMapper.selectProductById(id);
		
		productMapper.updateProductById(id, name, category, description);
		
		productDetailBO.updateProductDetailByProductId(product.getId(), color, size, quantity, price, sale);
		
		if (imgFile1 != null) {
			productPicsBO.addProdcutPics(product.getId(), imgFile1);
		}
	}
}
