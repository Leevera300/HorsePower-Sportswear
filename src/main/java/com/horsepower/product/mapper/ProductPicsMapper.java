package com.horsepower.product.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductPicsMapper {
	
	public void insertProductPics(
			@Param("productId") int productId,
			@Param("imagePath") String imagePath);

}
