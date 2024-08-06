package com.horsepower.product.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.horsepower.common.FileManagerService;
import com.horsepower.product.mapper.ProductPicsMapper;

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
}
