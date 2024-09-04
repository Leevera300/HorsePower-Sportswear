package com.horsepower.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FileManagerService {
	
	// 실제 업로드가 된 이미지가 저장되 서버의 경로  / 끝에 붙이기
		//public static final String FILE_UPLOAD_PATH = "C:\\Users\\201-10\\Desktop\\Michael\\horsepower\\workspace\\images/";
		//public static final String FILE_UPLOAD_PATH = "C:\\Users\\micha\\Desktop\\coding\\horsepower\\workspace\\images/";
		//AWS
		public static final String FILE_UPLOAD_PATH = "/home/ec2-user/images/";
		
		// input: MultipartFile userLoginId 난 나중에 userId 쓰자
		// output: String(이미지 경로)
		public String uploadFile(MultipartFile file) {
			// folder(directory) 생성
			// 예:1_17348493489/sun.png
			String directoryName = "horsepowerProduct_" + System.currentTimeMillis();
			// D:\Michael (마이클)\project\horsepower\horsepower_workspace\images/aaaa_17348493489/
			String filePath = FILE_UPLOAD_PATH + directoryName + "/";
			
			// 폴더 생성
			File directory = new File(filePath);
			log.info("$$$$$$$$$$$$$ [FileManagerService 파일업로드] dir:{}", directory);
			if(directory.mkdir() == false) {
				// 폴더 생성 시 실패하면 경로를 null로 리턴
				log.warn("$$$$$$[FileManagerService 파일업로드] 디렉토리 생성 실패. path:{}", filePath);
				return null;
			}
			
			// 파일 업로드
			try {
				byte[] bytes = file.getBytes();
				// ★★★★★★ 한글으로 된 이미지는 업로드 불가하므로 나중에 영문자로 바꾸기
				Path path = Paths.get(filePath + file.getOriginalFilename());
				Files.write(path, bytes);
			} catch (IOException e) {
				e.printStackTrace();
				return null; // 이미지 업로드 실패시 경로 null
			}
			
			// 파일 업로드가 성공하면
			// 이미지 url path 를 리턴
			// 주소는 이렇게 될것이다.(예언)
			// /images/aaaa_17348493489/sun.png
			return "/images/" + directoryName + "/" + file.getOriginalFilename();
			
		}
		
		// 파일 삭제
		// input: 이미지 경로(path) 	output: X
		public void deleteFile(String imagePath) {
			Path path = Paths.get(FILE_UPLOAD_PATH + imagePath.replace("/images/", ""));
			
			// 삭제할 이미지가 존재하는가?
			if (Files.exists(path)) {
				// 이미지 삭제
				try {
					Files.delete(path);
				} catch (IOException e) {
					log.warn("[FileManagerService 파일삭제] 이미지 삭제 실패. path:{}", path.toString());
					return;
				}
						
				// 폴더(디렉토리) 삭제
				path = path.getParent();
				if (Files.exists(path)) {
					try {
						Files.delete(path);
					} catch (IOException e) {
						log.warn("[FileManagerService 파일삭제] 디렉토리 삭제 실패. path:{}", path.toString());
					}
				}
			}
		}

}
