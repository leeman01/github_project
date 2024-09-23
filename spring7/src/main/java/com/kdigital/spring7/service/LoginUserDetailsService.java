package com.kdigital.spring7.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kdigital.spring7.dto.LoginUserDetails;
import com.kdigital.spring7.dto.UserDTO;
import com.kdigital.spring7.entity.UserEntity;
import com.kdigital.spring7.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LoginUserDetailsService implements UserDetailsService {
	final UserRepository userRepository;
	
	// UserId 검증 로직 추가. DB 테이블에서 데이터를 가져옴
	// 사용자가 로그인을 하면 SecurityConfig 가로채서 데이터를 여기로 전달
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

		UserEntity userEntity = userRepository.findById(userId)
				.orElseThrow(() -> {
					throw new UsernameNotFoundException("error 발생");
				});
		
		UserDTO userDTO = UserDTO.toDTO(userEntity);
		
		// 반환을 UserDetail로 해야하므로 userDTO를 
		return new LoginUserDetails(userDTO);
	}
}