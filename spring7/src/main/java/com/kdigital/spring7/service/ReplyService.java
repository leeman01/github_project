package com.kdigital.spring7.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kdigital.spring7.dto.ReplyDTO;
import com.kdigital.spring7.entity.BoardEntity;
import com.kdigital.spring7.entity.ReplyEntity;
import com.kdigital.spring7.repository.BoardRepository;
import com.kdigital.spring7.repository.ReplyRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReplyService {
	final BoardRepository boardRepository;
	final ReplyRepository replyRepository;
	
	@Transactional
	public ReplyDTO replyInsert(ReplyDTO replyDTO) {
		// 부모의 게시글이 존재하는지 확인
		Optional<BoardEntity> boardEntity = boardRepository.findById(replyDTO.getBoardNum());
		if (boardEntity.isPresent()) {
			BoardEntity entity = boardEntity.get(); // 부모의 게시물을 꺼냄
			
			ReplyEntity replyEntity = ReplyEntity.toEntity(replyDTO, entity); // 최종적으로 ReplyEntity가 저장되어야 함
			
			ReplyEntity temp = replyRepository.save(replyEntity);
			return ReplyDTO.toDTO(temp, replyDTO.getBoardNum());
		}
		return null;
	}

	public List<ReplyDTO> replyAll(Long boardNum) {
		Optional<BoardEntity> boardEntity = boardRepository.findById(boardNum);
		
		List<ReplyEntity> replyEntityList = replyRepository.findAllByBoardEntityOrderByReplyNumDesc(boardEntity);
		
		// Entity -> DTO
		List<ReplyDTO> replyDTOList = new ArrayList<>();
		replyEntityList.forEach((entity) -> replyDTOList.add(ReplyDTO.toDTO(entity, boardNum)));
		
		System.out.println("★" + replyDTOList + "★"); // 확인용 코드
		
		return replyDTOList;
	}

	/**
	 * 댓글 삭제
	 * @param replyNum : 삭제하려는 댓글 번호
	 * @return
	 */
	public boolean replyDelete(Long replyNum) {
		replyRepository.deleteById(replyNum); // 삭제 후
		
		return replyRepository.existsById(replyNum);
	}
}