package com.horsepower.product.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.horsepower.product.domain.ProductDetail;

@Mapper
public interface ProductDetailMapper {

	public void insertProdcutDetail(
			@Param("productId") int productId, 
			@Param("color") String color, 
			@Param("size") String size, 
			@Param("quantity") int quantity, 
			@Param("price") double price, 
			@Param("sale") Integer sale);
	
	public List<ProductDetail> selectProductDetailListByProductId(int productId);
	
	public List<ProductDetail> selectProductDetailListById(int id);
	
	public ProductDetail selectProductDetailByProductIdAndColorAndSize(
			@Param("productId") int productId,
			@Param("color") String color, 
			@Param("size") String size);
	
	public ProductDetail selectProductDetailById(int Id);
	
	public void deleteProductDetailByProductId(int productId);
	
	public void updateProductDetailByProductId(
			@Param("productId") int productId, 
			@Param("color") String color, 
			@Param("size") String size, 
			@Param("quantity") int quantity, 
			@Param("price") double price, 
			@Param("sale") Integer sale);

	public void deleteProductDetailById(int id);


	
}
