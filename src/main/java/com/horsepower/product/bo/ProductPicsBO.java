package com.horsepower.product.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.horsepower.common.FileManagerService;
import com.horsepower.product.domain.ProductPics;
import com.horsepower.product.mapper.ProductPicsMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductPicsBO {

	@Autowired
	private FileManagerService fileManagerService;
	
	@Autowired
	private ProductPicsMapper productPicsMapper;
	
	public void addProdcutPics(int productId, MultipartFile file1) {
		
		String imagePath = null;
		if(file1 != null) {
			// 파일이 업로드 할 이미지가 있을 때에만 업로드
			imagePath = fileManagerService.uploadFile(file1);
		}
		
		productPicsMapper.insertProductPics(productId, imagePath);
	}
	
	public List<ProductPics> getProductPicsByProductId(int productId) {
		
		return productPicsMapper.selectProductPicsByProductId(productId);
	}
	
	public void deleteProductPicsByProductId(int productId) {
		
		List<ProductPics> productPicsList = productPicsMapper.selectProductPicsByProductId(productId);
		
		for (ProductPics productPic : productPicsList) {
			
			if (productPic == null) {
				log.warn("[DELETE PRODUCT] ProductPic is null. productId:{}", productId);
				return;
			}
			
			int rowCount = productPicsMapper.deleteProductPicsByProductId(productId);
			
			if (rowCount == 1 && productPic.getImagePath() != null) {
				fileManagerService.deleteFile(productPic.getImagePath());
			}
		}
	}
}
