package com.horsepower.config.auth.dto;

import com.horsepower.user.entity.UserEntity;

import lombok.Getter;

@Getter
public class SessionUser {

	private String name;
    private String email;
    private String picture;
	
    public SessionUser(UserEntity user) {
        this.email = user.getEmail();
    }
}
