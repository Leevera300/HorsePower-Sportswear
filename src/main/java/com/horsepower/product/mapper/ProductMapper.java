package com.horsepower.product.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.horsepower.product.domain.Product;

@Mapper
public interface ProductMapper {

	public Product insertProdcut(
			@Param("productName") String productName, 
			@Param("category")String category, 
			@Param("productDesc")String productDesc);
	
}
