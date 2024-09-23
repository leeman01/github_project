package com.kdigital.jpa01.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name="myuser")
public class User {	
	@Id
	private String email;
	private String name;
	
	@Column(name="join_date")
	private LocalDateTime joinDate;
	
	// 생성자 2종류
	// SETTER , GETTER ,TO STRING

	public User() {
	}
	
	
	public User(String email, String name, LocalDateTime joinDate) {
		super();
		this.email = email;
		this.name = name;
		this.joinDate = joinDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(LocalDateTime joinDate) {
		this.joinDate = joinDate;
	}
	
	@Override
	public String toString() {
		return "User [email=" + email + ", name=" + name + ", joinDate=" + joinDate + "]";
	}

	

}
