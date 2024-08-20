package com.horsepower.delievery.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.horsepower.delievery.entity.BillingEntity;
import com.horsepower.delievery.repository.BillingRepository;
import com.horsepower.order.entity.DelieveryInfo;

@Service
public class BillingBO {
	
	@Autowired
	private BillingRepository billingRepository;

	public void addBillingInfo(DelieveryInfo delieveryInfo, String orderNumber) {
		if (delieveryInfo.getBillingFirstName() == null) {
			billingRepository.save(BillingEntity.builder()
	                .orderNumber(orderNumber)
	                .countryRegion(delieveryInfo.getCountry())
	                .firstName(delieveryInfo.getFirstName())
	                .lastName(delieveryInfo.getLastName())
	                .adress1(delieveryInfo.getAddress1())
	                .adress2(delieveryInfo.getAddress2())
	                .postalCode(delieveryInfo.getPostalCode())
	                .city(delieveryInfo.getCity())
	                .state(delieveryInfo.getState())
	                .phoneNumber(delieveryInfo.getPhone())
	                .build());
		} else {
			billingRepository.save(BillingEntity.builder()
	                .orderNumber(orderNumber)
	                .countryRegion(delieveryInfo.getBillingCountry())
	                .firstName(delieveryInfo.getBillingFirstName())
	                .lastName(delieveryInfo.getBillingLastName())
	                .adress1(delieveryInfo.getBillingAddress1())
	                .adress2(delieveryInfo.getBillingAddress2())
	                .postalCode(delieveryInfo.getBillingPostalCode())
	                .city(delieveryInfo.getBillingCity())
	                .state(delieveryInfo.getBillingState())
	                .phoneNumber(delieveryInfo.getBillingPhone())
	                .build());
		}
	}

}
