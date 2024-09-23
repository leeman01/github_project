package com.kdigital.spring7.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kdigital.spring7.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String> {
	
}
