package com.kdigital.spring7.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;
import org.springframework.data.annotation.LastModifiedDate;

import com.kdigital.spring7.dto.BoardDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

@Entity
@Table(name="board")
public class BoardEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="board_num")
	private Long boardNum;
	
	@Column(name="board_writer")
	private String boardWriter;
	
	@Column(name="board_title")
	private String boardTitle;
	
	@Column(name="board_content")
	private String boardContent;
	
	@Column(name="hit_count")
	private int hitCount;
	
	@Column(name="create_date")
	@CreationTimestamp // 게시글이 처음 생성될 때 자동으로 날짜 세팅
	private LocalDateTime createDate;
	
	@Column(name="update_date")
	@LastModifiedDate // 게시글이 수정될 때 자동으로 수정된 마지막 날짜/시간 세팅
	private LocalDateTime updateDate;
	
	// 첨부파일이 있을 경우 추가
	@Column(name="original_file_name")
	private String originalFileName;
	
	@Column(name="saved_file_name")
	private String savedFileName;
	
	// 댓글 개수 처리
	@Formula("(SELECT count(1) FROM reply r WHERE board_num = r.board_num)")
	private int replyCount;
	
	/*
	 * 참고: 양방향 관계가 설정되어 있는 경우
	 * one에 해당하는 테이블 엔트리에 설정함
	 * - mappedBy: 양방향 참조를 할 경우 one에 해당하는 테이블 엔트리 값
	 * - CascadeType.REMOVE: 테이블을 설정할 때 DB에서 on delete cascade 옵션을 주는 것과 동일
	 * - fetch = FetchType.LAZY: 지연호출 / fetch = FetchType.EAGER: 즉시호출
	 * - orphanRemoval: 일대다 관계를 가지고 있을 때 지정하는 옵션
	 * 					자식 엔티티의 변경이 일어나면 JPA(insert -> update -> delete)
	 * 					PK(Join column)의 값이 Null이 되는 자식을 고아객체라고 하는데, 고아객체는 연결점을 잃게 됨
	 * 					orphanRemoval = true로 설정해두면 고아객체가 삭제됨
	 */
	
	/*
	 * 현재 게시글인 Board는 일대다 관계인데 이러한 단방향인 상태에서 사실 필요하진 않으나,
	 * 프로젝트 중에 필요할 수 있어서 적어둠.
	 * 자식이 여러 개 올 경우를 위해 쿼리문을 역순으로 정렬하기 위함 (양방향일 때 사용)
	 * (게시판은 양방향 관계가 아니기 때문에 필요하지 않음)
	 */
//	@OneToMany(
//			mappedBy = "boardEntity"
//			, cascade = CascadeType.REMOVE
//			, fetch = FetchType.LAZY
//			, orphanRemoval = true
//			)
//	
//	@OrderBy("reply_num asc")
//	private List<ReplyEntity> replyEntity = new ArrayList<>();
	
	// DTO를 받아서 Entity로 반환
	public static BoardEntity toEntity(BoardDTO boardDTO) {
		return BoardEntity.builder()
				.boardNum(boardDTO.getBoardNum())
				.boardWriter(boardDTO.getBoardWriter())
				.boardTitle(boardDTO.getBoardTitle())
				.boardContent(boardDTO.getBoardContent())
				.hitCount(boardDTO.getHitCount())
				.originalFileName(boardDTO.getOriginalFileName())
				.savedFileName(boardDTO.getSavedFileName())
//				.createDate(boardDTO.getCreateDate())
//				.updateDate(boardDTO.getUpdateDate())
				.build();
	}
}