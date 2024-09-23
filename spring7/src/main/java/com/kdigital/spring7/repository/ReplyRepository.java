package com.kdigital.spring7.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kdigital.spring7.entity.BoardEntity;
import com.kdigital.spring7.entity.ReplyEntity;

public interface ReplyRepository extends JpaRepository<ReplyEntity, Long> {

	List<ReplyEntity> findAllByBoardEntityOrderByReplyNumDesc(Optional<BoardEntity> boardEntity);

}
