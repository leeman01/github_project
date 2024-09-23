package com.kdigital.spring7.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kdigital.spring7.dto.Iris;
import com.kdigital.spring7.service.PredictService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PredictController {
	
	final PredictService service;
	
	/**
	 * 붓꽃 분류(예측)을 위한 화면 요청
	 * @return
	 */
	@GetMapping("/predict")
	public String predict() {
		return "iris";
	}
	
	@PostMapping("/predict")
	@ResponseBody
	public Map<String, Object> predict(@ModelAttribute Iris iris) {
		log.info("{}", iris.toString());
		
		Map<String, Object> result = service.predictRest(iris);
		
		return result;
	}
	
}
