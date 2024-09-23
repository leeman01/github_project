package com.kdigital.spring7.dto;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// Security로 login하는 DTO
@Getter
@Setter
@ToString
public class LoginUserDetails implements UserDetails {
	private static final long serialVersionUID = 1L;
    private String userId;
    private String userPwd;
    private String userName;
    private String email;
    private String roles;
    private Boolean enabled;
    
    // 생성자
    public LoginUserDetails(UserDTO userDTO) {
    	this.userId = userDTO.getUserId();
    	this.userPwd = userDTO.getUserPwd();
    	this.userName = userDTO.getUserName();
    	this.email = userDTO.getEmail();
    	this.roles = userDTO.getRoles();
    	this.enabled = userDTO.getEnabled();
    }
	
    // 사용자의 Role 정보 반환
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		System.out.println("성공");
		Collection<GrantedAuthority> collection = new ArrayList<>();
		collection.add(new GrantedAuthority() {
			private static final long serialVersionUID = 1L;

			@Override
			public String getAuthority() {
				return roles;
			}
		});
		return collection;
	}

	// 비밀번호 확인을 위함
	@Override
	public String getPassword() {
		return this.userPwd;
	}

	// Security가 설정한 메소드 -> UserId(사용자 아이디)
	@Override
	public String getUsername() {
		return this.userId;
	}

	// 사용자 정의 메소드(View단에서 사용할 예정)
	public String getUserName() {
		return this.userName;
	}
}
