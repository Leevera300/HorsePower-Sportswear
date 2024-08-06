package com.horsepower.product.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductDetailMapper {

	public void insertProdcutDetail(
			@Param("productId") int productId, 
			@Param("color") String color, 
			@Param("size") String size, 
			@Param("quantity") int quantity, 
			@Param("price") double price, 
			@Param("sale") Integer sale);
}
