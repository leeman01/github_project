package com.kdigital.spring7.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kdigital.spring7.dto.ReplyDTO;
import com.kdigital.spring7.service.ReplyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@Slf4j
@RequestMapping("/reply")
@RequiredArgsConstructor
public class ReplyController {
	
	final ReplyService replyService;
	
	@PostMapping("/replyInsert")
	public ReplyDTO replyInsert(@ModelAttribute ReplyDTO replyDTO) {
        log.info("댓글 삽입: {}", replyDTO.toString());
        replyService.replyInsert(replyDTO);
        
        return replyDTO; // 수정할 내용!
    }

	/**
	 * 전달받은 게시글의 댓글 목록 전체 조회
	 * @param param
	 * @return
	 */
    @GetMapping("/replyAll")
    public List<ReplyDTO> replyAll(@RequestParam(name="boardNum") Long boardNum) {
        log.info("게시글 번호: {}", boardNum);

        List<ReplyDTO> list = replyService.replyAll(boardNum);
        return list;
    }
    
    @GetMapping("/replyDelete")
    public boolean replyDelete(@RequestParam(name="replyNum") Long replyNum) {
        log.info("삭제할 댓글 번호: {}", replyNum);

        return replyService.replyDelete(replyNum);
    }
    
}
