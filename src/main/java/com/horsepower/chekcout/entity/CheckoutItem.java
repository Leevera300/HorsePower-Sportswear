package com.horsepower.chekcout.entity;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class CheckoutItem {

	private int checkoutId;
    private String productName;
    private String color;
    private String size;
    private String quantity;


}
