package com.kdigital.spring7.dto;

import java.time.LocalDate;

import com.kdigital.spring7.entity.ReplyEntity;

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
public class ReplyDTO {
	private Long replyNum;
	private Long boardNum;
	private String replyWriter;
	private String replyText;
	private LocalDate createDate;
	
	// Entity -> DTO
	public static ReplyDTO toDTO (ReplyEntity entity, Long boardNum) {
		return ReplyDTO.builder()
				.replyNum(entity.getReplyNum())
				.replyWriter(entity.getReplyWriter())
				.replyText(entity.getReplyText())
				.boardNum(boardNum)
				.createDate(entity.getCreateDate())
				.build();
	}
}