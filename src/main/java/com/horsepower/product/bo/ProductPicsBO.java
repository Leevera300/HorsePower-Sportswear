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
	
	public void addProdcutPics(int productId, MultipartFile[] files) {
		
		String imagePath = null;
//		if(files != null) {
//			imagePath = fileManagerService.uploadFile(files);
//			// 파일이 업로드 할 이미지가 있을 때에만 업로드
//		}
//		imagePath = fileManagerService.uploadFile(files);
		log.info("$$$$$$$$$$$$$$$$$$$$$ [IMAGES 파일업로드] files:{}", files);
		for (MultipartFile file : files) {
			if (file.isEmpty()) {
				continue;
			}
			imagePath = fileManagerService.uploadFile(file);
			
			productPicsMapper.insertProductPics(productId, imagePath);
		}
			
			// 파일이 업로드 할 이미지가 있을 때에만 업로드
		
		
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

	public ProductPics getProductPicByProductIdLimit1(int productId) {
		return productPicsMapper.selectProductPicByProductIdLimit1(productId);
	}

	public void deleteProductPicById(int id) {
		productPicsMapper.deleteProductPicById(id);
		
	}

}
