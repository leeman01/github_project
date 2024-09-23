package com.kdigital.spring7.util;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

// Multipart 파일을 받아서 savedFileName을 만드는 작업
public class FileService {
	// 1) 서버에 디렉토리가 없으면 디렉토리 생성
	// 2) 전달받은 업로드 파일에서 원본 파일명을 꺼냄 -> 저장용 파일명 생성(랜덤값)
	// 3) 1번과 2번을 이용하여 실제 디렉토리에 파일을 저장하는 작업
	// 4) savedFileName(저장 파일명)을 DB에도 저장할 수 있도록 반환하는 작업
	
	public static String saveFile(MultipartFile uploadFile, String uploadPath) {
		/*
		 *  파일이 첨부되면 디렉토리가 있는지 확인
		 *  -> 디렉토리가 있으면 그대로 사용하고 없으면 디렉토리 생성
		 */
		if (!uploadFile.isEmpty()) {
			File path = new File(uploadPath);
			if (!path.isDirectory()) {
				path.mkdirs();
			}
		}
		
		// 원본 파일명 추출
		String originalFileName = uploadFile.getOriginalFilename(); // 이력서.hwp
		
		// 랜덤값 발생
		String uuid = UUID.randomUUID().toString();
		
		// 원본 파일명에서 파일명과 확장명을 분리
		String filename;
		String ext;
		String savedFileName;
		
		// .의 위치를 찾음
		int position = originalFileName.lastIndexOf('.');
		
		// 확장자 추출
		// 확장자가 없는 경우 (값: -1)
		if (position == -1) {
			ext = "";
		} else { // 확장자가 있는 경우 (예: 이력서.jpg)
			ext = originalFileName.substring(position + 1); // jpg
		}
		
		// 파일명 추출
		filename = originalFileName.substring(0, position);
		
		//savedFileName 작성
		savedFileName = filename + "_" + uuid + ext;
		
		// 서버 공간에 파일 저장
		File serverFile = null;
		serverFile = new File(uploadPath + "/" + savedFileName);
		
		try {
			uploadFile.transferTo(serverFile);
		} catch (IOException e) {
			savedFileName = null; // 파일을 저장하는 도중 오류가 발생하는 경우, DB에 파일명도 저장되면 안됨
			e.printStackTrace();
		}
		
		return savedFileName; // DB에 저장할 변경된 파일명
	}
	
	// 서버 공간에 저장된 파일명 삭제(경로 + savedFileName = fullPath로 받음)
	public static boolean deleteFile(String fullPath) {
		boolean result = false; // 삭제 여부 반환
		
		File delFile = new File(fullPath);
		
		if (delFile.isFile()) {
			result = delFile.delete();
		}
		return result;
	}
}
