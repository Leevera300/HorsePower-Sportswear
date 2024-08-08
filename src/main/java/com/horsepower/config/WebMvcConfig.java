package com.horsepower.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.horsepower.common.FileManagerService;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	
	// 이미지 path와  서버의 업로드 된 실제 이미지와 매핑 설정
	@Override
	public void addResourceHandlers(
			ResourceHandlerRegistry registry) {
		
		registry
		.addResourceHandler("/images/**") // web path	http://localhost/images/aaaa_1721209535801/sparrow-8848051_960_720.jpg
		.addResourceLocations("file:///" + FileManagerService.FILE_UPLOAD_PATH); // 실제 이미지 파일 위치 D:\Michael (마이클)\6_spring_project\memo\memo_workspace\images\aaaa_1721209535801
	}
}
