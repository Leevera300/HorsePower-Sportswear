package com.horsepower.order.entity;

import com.horsepower.product.domain.ProductInfo;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class ProductOrder {

	private ProductInfo productInfo;
    private OrderEntity order;
}
