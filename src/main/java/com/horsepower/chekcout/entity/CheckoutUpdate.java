package com.horsepower.chekcout.entity;

import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CheckoutUpdate {

	
	private List<Integer> checkoutIds;
	
	private List<Integer> quantities;
}
