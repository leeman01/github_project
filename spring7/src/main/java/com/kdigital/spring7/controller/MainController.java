package com.kdigital.spring7.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.kdigital.spring7.dto.LoginUserDetails;

@Controller
public class MainController {

	/**
	 * 첫 화면 요청
	 * @return
	 */
	@GetMapping({"/", ""})
	public String index(
			@AuthenticationPrincipal LoginUserDetails loginUser
			, Model model
			) {
		// 인증을 받은 사용자
		if (loginUser != null) {
			model.addAttribute("loginName", loginUser.getUserName());
		}
		return "index";
	}
	
}
