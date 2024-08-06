package com.horsepower.product.domain;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class ProductDetail {

	private int id;
	private int productId;
	private String color;
	private String size;
	private int quantity;
	private Integer sale;
	private double price;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
