package com.kdigital.spring7.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;


@Controller
@Slf4j
public class AdminController {
	@GetMapping("/admin")
	public String admin() {
		log.info("admin 페이지 요청");
		return "admin/adminPage";
	}
	
}
