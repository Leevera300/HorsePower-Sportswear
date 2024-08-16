package com.horsepower.product.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.horsepower.product.domain.ProductPics;

@Mapper
public interface ProductPicsMapper {
	
	public void insertProductPics(
			@Param("productId") int productId,
			@Param("imagePath") String imagePath);

	public List<ProductPics> selectProductPicsByProductId(int productId);
	
	public int deleteProductPicsByProductId(int productId);

	public ProductPics selectProductPicByProductIdLimit1(int productId);

}
