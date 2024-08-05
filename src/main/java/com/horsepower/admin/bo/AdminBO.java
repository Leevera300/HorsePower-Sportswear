package com.horsepower.admin.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.horsepower.user.bo.UserBO;

@Service
public class AdminBO {

	@Autowired
	private UserBO userBO;
	
	
}
