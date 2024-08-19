package com.horsepower.delievery.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.horsepower.delievery.entity.DelieveryEntity;
import com.horsepower.delievery.repository.DelieveryRepository;
import com.horsepower.order.entity.DelieveryInfo;

@Service
public class DelieveryBO {
	
	@Autowired
	private DelieveryRepository delieveryRepository;

	public void addDelieveryInfo(DelieveryInfo delieveryInfo, String orderNumber) {
		delieveryRepository.save(DelieveryEntity.builder()
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
	}

}
