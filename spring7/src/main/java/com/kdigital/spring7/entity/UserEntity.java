package com.kdigital.spring7.entity;

import com.kdigital.spring7.dto.UserDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder

@Entity
@Table(name="boarduser")
public class UserEntity {
	@Id
	@Column(name="user_id")
    private String userId;
	
	@Column(name="user_pwd", nullable=false)
    private String userPwd;
	
	@Column(name="user_name", nullable=false)
    private String userName;
	
	@Column(name="email", nullable=false)
    private String email;
	
    private String roles;
    private Boolean enabled;
    
    // DTO를 전달받아 Entity로 반환
    public static UserEntity toEntity(UserDTO userDTO) {
        return UserEntity.builder()
              .userId(userDTO.getUserId())
              .userName(userDTO.getUserName())
              .userPwd(userDTO.getUserPwd())
              .email(userDTO.getEmail())
              .roles(userDTO.getRoles())
              .enabled(userDTO.getEnabled())
              .build();
              
    }
}

