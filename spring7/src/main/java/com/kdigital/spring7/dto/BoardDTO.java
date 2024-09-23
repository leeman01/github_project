package com.kdigital.spring7.dto;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import com.kdigital.spring7.entity.BoardEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class BoardDTO {
	private Long boardNum;
	private String boardWriter;
	private String boardTitle;
	private String boardContent;
	private int hitCount;
	private LocalDateTime createDate;
	private LocalDateTime updateDate;
	private int replyCount;
	
	// 업로드하는 파일을 받는 멤버 변수
	private MultipartFile uploadFile;
	
	private String originalFileName; 	// 파일의 원래 파일명
	private String savedFileName; 		// 하드디스크에 저장될 때 사용되는 변경된 파일명
	
	// Entity를 받아서 DTO로 반환
	public static BoardDTO toDTO(BoardEntity boardEntity) {
		return BoardDTO.builder()
				.boardNum(boardEntity.getBoardNum())
				.boardWriter(boardEntity.getBoardWriter())
				.boardTitle(boardEntity.getBoardTitle())
				.boardContent(boardEntity.getBoardContent())
				.hitCount(boardEntity.getHitCount())
				.createDate(boardEntity.getCreateDate())
				.updateDate(boardEntity.getUpdateDate())
				.originalFileName(boardEntity.getOriginalFileName())
				.savedFileName(boardEntity.getSavedFileName())
				.replyCount(boardEntity.getReplyCount()) // 댓글 개수
				.build();
	}

	// 생성자 -> 
	public BoardDTO(
		Long boardNum
		, String boardWriter
		, String boardTitle
		, int hitCount
		, LocalDateTime createDate
		, String originalFileName
		, int replyCount) {
		this.boardNum = boardNum;
		this.boardTitle = boardTitle;
		this.boardWriter = boardWriter;
		this.hitCount = hitCount;
		this.createDate = createDate;
		this.originalFileName = originalFileName;
		this.replyCount = replyCount;
	}
}