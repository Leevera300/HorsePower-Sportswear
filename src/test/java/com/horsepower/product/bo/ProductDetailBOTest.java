package com.horsepower.product.bo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class ProductDetailBOTest {

	@Autowired
	ProductDetailBO pDBO;
	
	@Transactional
	@Test
	void test() {
		pDBO.addProductDetail(12, "asd", "asd", 1, 3, null);
		
	}

}
