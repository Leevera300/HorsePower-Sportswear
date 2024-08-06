package com.horsepower.product.domain;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class Product {

	private int id;
	private String name;
	private String description;
	private String category;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
