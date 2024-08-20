package com.horsepower.order.entity;

import java.util.List;

import com.horsepower.chekcout.entity.CheckoutItem;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class DelieveryInfo {

	private String country;
	private String FirstName;
	private String LastName;
	private String address1;
	private String address2;
	private int postalCode;
	private String city;
	private String state;
	private String phone;
	private String billingCountry;
	private String billingFirstName;
	private String billingLastName;
	private String billingAddress1;
	private String billingAddress2;
	private Integer billingPostalCode;
	private String billingCity;
	private String billingState;
	private String billingPhone;
	
	private List<CheckoutItem> checkoutItems;
}
