package com.horsepower.product.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.horsepower.product.domain.Product;

@Mapper
public interface ProductMapper {

	public void insertProduct(Product product);
	
	public List<Product> selectProduct();
	
	public Product selectProductById(int id);
	
}
