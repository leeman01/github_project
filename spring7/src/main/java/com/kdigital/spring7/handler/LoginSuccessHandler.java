package com.kdigital.spring7.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(
			HttpServletRequest request
			, HttpServletResponse response
			, Authentication authentication) throws IOException, ServletException {
		
//		log.info("로그인 성공");
		
		List<String> roleNames = new ArrayList<>();
		
		authentication.getAuthorities().forEach((auth) -> {
			roleNames.add(auth.getAuthority());
		});
		
		log.info("role names: {}", roleNames);
		
		if (roleNames.contains("ROLE_ADMIN")) {
			response.sendRedirect("/admin");
		} else if (roleNames.contains("ROLE_USER")) {
			response.sendRedirect("/");
			return;
		}
		
	}

}
