package com.horsepower.product.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.horsepower.product.domain.ProductDetail;
import com.horsepower.product.mapper.ProductDetailMapper;

@Service
public class ProductDetailBO {
	
	@Autowired
	private ProductDetailMapper productDetailMapper;

	public void addProductDetail(int productId, List<ProductDetail> productDetail) {
		
		for (int i = 0; productDetail.size() > i; i++) {
			ProductDetail detail = productDetail.get(i);
			productDetailMapper.insertProdcutDetail(productId, detail.getColor(), detail.getSize(),
					detail.getQuantity(), detail.getPrice(), detail.getSale());
		}
	}
	
	public List<ProductDetail> getProductDetailListByProductId(int productId) {
		
		return productDetailMapper.selectProductDetailListByProductId(productId);
	}
	
	public void deleteProductDetailByProductId(int productId) {
		
		productDetailMapper.deleteProductDetailByProductId(productId);
	}
	
	public void updateProductDetailByProductId(int productId, List<ProductDetail> productDetail) {
		
		for (int i = 0; productDetail.size() > i; i++) {
			ProductDetail detail = productDetail.get(i);
			productDetailMapper.updateProductDetailByProductId(productId, detail.getColor(), detail.getSize(),
					detail.getQuantity(), detail.getPrice(), detail.getSale());
		}
	}
}
