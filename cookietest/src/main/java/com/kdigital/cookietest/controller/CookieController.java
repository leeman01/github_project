package com.kdigital.cookietest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class CookieController {
	
	@GetMapping("/cookieSave")
	public String cookieSave(HttpServletResponse response) {
		//쿠키생성
		Cookie cookie1 = new Cookie("test1","1111");
		Cookie cookie2 = new Cookie("test2","2222");	
		Cookie cookie3 = new Cookie("test3","3333");
		
		//쿠키정보가 클라이언트에 유지되는 시간 설정
		cookie1.setMaxAge(24*60*60);
		cookie2.setMaxAge(24*60*60);
		cookie3.setMaxAge(24*60*60);
		
		//쿠키정보를 클라이언트에 저장
		response.addCookie(cookie1);
		response.addCookie(cookie2);
		response.addCookie(cookie3);
	
	return "cookieResult";
	}
		@GetMapping("/cookieRead")
		public String cookieRead(
				@CookieValue(name="test1", defaultValue="큐키없음")String temp1
				,@CookieValue(name="test2", defaultValue="None")String temp2
				,@CookieValue(name="test4", defaultValue="이런 큐키없음")String temp4
				) {
			log.info("첫번째 쿠키값:{}",temp1);
			log.info("두번째 쿠키값:{}",temp2);
			log.info("네번째 쿠키값:{}",temp4);
		
		
		
		return "redirect:/";
	}
		@GetMapping("/cookieDel")
		public String cookieDel(HttpServletResponse response) {
			//쿠키생성
			Cookie cookie1 = new Cookie("test1",null);
			Cookie cookie2 = new Cookie("test2",null);	
			Cookie cookie3 = new Cookie("test3",null);
			
			//쿠키정보가 클라이언트에 유지되는 시간 설정
			cookie1.setMaxAge(0);
			cookie2.setMaxAge(0);
			cookie3.setMaxAge(0);
			
			//쿠키정보를 클라이언트에 저장
			response.addCookie(cookie1);
			response.addCookie(cookie2);
			response.addCookie(cookie3);
			return "redirect:/";
		}
}					