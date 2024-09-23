package com.kdigital.jpa01;

import java.time.LocalDateTime;

import com.kdigital.jpa01.entity.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class UserInsert {

	public static void main(String[] args) {
		//1 persistence.xml 의 설정 정보를 읽어 factory 객체 생성
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpastudy");
		//2 위에서 생성한 factory를 이용해 실제로 접속할 수 있는 객체를 생성
		EntityManager manager = factory.createEntityManager();
		//3 트랜잭션을 시작하기 위한 객체생성
		EntityTransaction tx = manager.getTransaction();
		
		try {
			//4 트랜잭션을 시작시킨 후 entity 객체를 생성하여 저장한다
			tx.begin();
			//5 DB에 저장할 데이터를 가지고 있는 엔티티 객체를 생성한다
			User user = new User ("kim@naver.com", "김길동",LocalDateTime.now());
			// 6 실제로 데이터를 INSERT 하는 메소드를 실행한다
			manager.persist(user);
			//7 commit을 해야 저장된다
			tx.commit();
			System.out.println("저장완료!");}
		catch (Exception e) {
			tx.rollback();
			e.printStackTrace();}
		finally {
			manager.close();}
		factory.close();
		}
	}
