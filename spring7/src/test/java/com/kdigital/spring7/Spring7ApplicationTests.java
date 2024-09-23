package com.kdigital.spring7;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kdigital.spring7.entity.BoardEntity;
import com.kdigital.spring7.repository.BoardRepository;

@SpringBootTest
class Spring7ApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Autowired
	private BoardRepository repository;
	
	// 게시글 여러 개를 삽입하는 테스트 코드
	@Test
	void testInsertBoard() {
		String[] w = {"윤수", "이주연", "이재현", "김영훈", "김선우", "지창민"};
		String[] c = {"모든 건 너에게 달렸어",
				"난 지켜볼 수 밖에 없어",
				"끝이 날 건지 아닌지는",
				"너의 말 한마디로 결정돼",
				"여태 받던 싸늘한 느낌",
				"그저 착각일지도",
				"여기서 알아야 갈 수 있어",
				"Do you even love me now?",
				"숨을 죽이고 너의 대답을 기다릴게",
				"턴을 너에게 넘긴 채로",
				"만약 아니라 하더라도 말해 줘",
				"Baby, love me or leave me tonight"};
		
		for (int i = 0; i < 135; ++i) {
			int index = (int)(Math.random() * w.length);
			String writer = w[index];
			
			index = (int)(Math.random() * c.length);
			String content = c[index];
			
			String title = "제목: " + content;
			
			BoardEntity entity = new BoardEntity();
			entity.setBoardWriter(writer);
			entity.setBoardTitle(title);
			entity.setBoardContent(content);
			
			repository.save(entity);
		}
	}
}
