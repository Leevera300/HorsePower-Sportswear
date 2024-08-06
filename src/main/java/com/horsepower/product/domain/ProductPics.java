package com.horsepower.product.domain;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class ProductPics {

	private int id;
	private int productId;
	private String imagePath;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
