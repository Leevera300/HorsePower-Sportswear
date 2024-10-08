package com.horsepower.product.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.horsepower.product.domain.Product;

@Mapper
public interface ProductMapper {

	public void insertProduct(Product product);
	
	public List<Product> selectProduct(
			@Param("standardId") Integer standardId,
			@Param("direction") String direction,
			@Param("limit") int limit);
	
	public List<Product> selectProductOrderByCreatedAtDESC();
	
	public List<Product> selectProductOrderByUpdatedAt();
	
	public Product selectProductById(int id);
	
	public void deleteProductById(int id);
	
	public void updateProductById(
			@Param("id") int id, 
			@Param("name") String name, 
			@Param("category") String category,  
			@Param("description") String description);

	public int selectProudcutIdAsSort(String sort);

	public List<Product> selectProductByCategory(String category);
	
}
