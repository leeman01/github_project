package com.kdigital.cookietest.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller

public class SessionController {
	
	@GetMapping("/sessionSave")
	public String sessionSave(HttpSession session) {
		session.setAttribute("loginId", "hong");
		session.setAttribute("loginName", "홍길동");
		session.setAttribute("loginAge", 25);
	
		return "sessionResult";
	}
	
	@GetMapping("/sessionRead")
	public String sessionRead(HttpSession session) {
		if(session.getAttributeNames().hasMoreElements()) {
		String id = (String)session.getAttribute("loginId");
		String name = (String)session.getAttribute("loginName");
		int age  = (Integer)session.getAttribute("loginAge");
		
		log.info("로그인 아이디:{}",id);
		log.info("로그인 이름:{}",name);
		log.info("로그인 나이:{}",age);
		}else {
			log.info("세션정보가 저장되지 않았습니다");
		}
	
		return "redirect:/";
	}
	
	@GetMapping("/sessionDel")
	public String sessionDel(HttpSession session) {
//		session.removeAttribute("loginId");
//		session.removeAttribute("loginName");		
//		session.removeAttribute("loginAge");
		
		session.invalidate();
		
		return "redirect:/";
	}
}
