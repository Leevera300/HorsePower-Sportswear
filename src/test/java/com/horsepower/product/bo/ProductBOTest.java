package com.horsepower.product.bo;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class ProductBOTest {

	@Autowired
	ProductBO productBO;
	
//	@Transactional
//	@Test
//	void update() {
//		 MultipartFile multipartFile1 = new MockMultipartFile("file", "test.txt", "text/plain", "test file".getBytes(StandardCharsets.UTF_8) );
//		productBO.updateProductById(14, "name", "name", "name", multipartFile1, "name", "name", 12, 1, null);
//	}

}
