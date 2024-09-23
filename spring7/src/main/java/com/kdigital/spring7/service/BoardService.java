package com.kdigital.spring7.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kdigital.spring7.dto.BoardDTO;
import com.kdigital.spring7.entity.BoardEntity;
import com.kdigital.spring7.repository.BoardRepository;
import com.kdigital.spring7.util.FileService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {
	
	final BoardRepository boardRepository;

	// 페이징을 할 때 한 페이지에 출력할 글 개수
	@Value("${user.board.pageLimit}")
	private int pageLimit;
	
	// 업로드된 파일이 저장될 디렉토리 경로를 읽어옴
	@Value("${spring.servlet.multipart.location}")
	private String uploadPath;
	
	/**
	 * DB에 게시글 저장
	 * @param boardDTO : 저장해야 하는 게시글
	 */
	public void insertBoard(BoardDTO boardDTO) {
		log.info("저장 경로: " + uploadPath);
		
		// 첨부파일 처리
		String originalFileName = null;
		String savedFileName = null;
		
		// 파일 첨부 여부 확인
		if (!boardDTO.getUploadFile().isEmpty()) {
			savedFileName = FileService.saveFile(boardDTO.getUploadFile(), uploadPath);
			originalFileName = boardDTO.getUploadFile().getOriginalFilename();
			
			boardDTO.setOriginalFileName(originalFileName);
			boardDTO.setSavedFileName(savedFileName);
		}
		
		// 1) Entity로 변환
		BoardEntity boardEntity = BoardEntity.toEntity(boardDTO);
		
		// 2) save()로 데이터 저장
		boardRepository.save(boardEntity);
	}
	
	/**
	 * 게시글 목록 요청
	 * @param pageable 
	 * @param searchItem
	 * @param searchWord
	 * @return
	 */
	public Page<BoardDTO> selectAll(Pageable pageable, String searchItem, String searchWord) {
		int page = pageable.getPageNumber() - 1;
		// -1을 하는 이유: DB의 페이지 번호가 0부터 시작하기 때문에 사용자가 1 페이지를 요청해도 DB에서는 0 페이지 요청
		
//		List<BoardDTO> list = new ArrayList<>();
		
		// 3) 페이징이 추가된 조회
		Page <BoardEntity> entityList = null;
		
		switch(searchItem) {
		case "boardTitle" : 
			entityList = boardRepository.findByBoardTitleContains(
					searchWord, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "boardNum")));
			break;
		case "boardWriter" : 
			entityList = boardRepository.findByBoardWriterContains(
					searchWord, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "boardNum")));
			break;
		case "boardContent" : 
			entityList = boardRepository.findByBoardContentContains(
					searchWord, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "boardNum")));
			break;
		}
		
		/* 2) 검색 기능이 추가된 조회
		List<BoardEntity> entityList = null;
		
		switch(searchItem) {
		case "boardTitle" : 
			entityList = boardRepository.findByBoardTitleContains(
			searchWord, Sort.by(Sort.Direction.DESC, "boardNum"));
			break;
		case "boardWriter" : 
			entityList = boardRepository.findByBoardWriterContains(
			searchWord, Sort.by(Sort.Direction.DESC, "boardNum"));
			break;
		case "boardContent" : 
			entityList = boardRepository.findByBoardContentContains(
			searchWord, Sort.by(Sort.Direction.DESC, "boardNum"));
			break;
		}
		*/
		
		// 1) 단순 조회
//		List<BoardEntity> entityList = boardRepository.findAll(Sort.by(Sort.Direction.DESC, "boardNum"));
//		List<BoardEntity> entityList = boardRepository.findByBoardWriterContains(searchWord);
		
		// Entity를 DTO로 바꾸는 작업 수행
		Page<BoardDTO> list = null;
		
		// 일반 기능(검색)만 가지고 있는 형태
//		entityList.forEach((entity) -> list.add(BoardDTO.toDTO(entity)));
		
		// 페이징 형태의 list로 반환
		// 목록에서 사용할 필요한 데이터만 간추리기 (생성자 만듦)
		list = entityList.map
				(board -> new BoardDTO(
						board.getBoardNum(),
						board.getBoardWriter(),
						board.getBoardTitle(),
//						board.getBoardContent(),
						board.getHitCount(),
						board.getCreateDate(),
						board.getOriginalFileName(), // 뷰단에서 사용
						board.getReplyCount()
						)
				);
		return list;
	}

	/**
	 * PK에 해당하는 boardNum 값을 이용하여 글 한 개 조회
	 * @param boardNum
	 * @return
	 */
	public BoardDTO selectOne(Long boardNum) {
		Optional<BoardEntity> entity = boardRepository.findById(boardNum);
		
		// 데이터를 꺼내 boardDTO로 변환
		if (entity.isPresent()) {
			BoardEntity temp = entity.get();
			return BoardDTO.toDTO(temp);
		}
		return null;
	}

	/**
	 * 전달받은 글 번호(boardNum)를 DB에서 삭제
	 * @param boardNum
	 */
	@Transactional
	public void deleteOne(Long boardNum) {
		// 글 번호에 해당하는 글이 존재하는지 읽어옴
		Optional<BoardEntity> entity = boardRepository.findById(boardNum);
		
		if (entity.isPresent()) {
			BoardEntity board = entity.get();
			String savedFileName = board.getSavedFileName();

			// 첨부 파일이 있으면 파일을 삭제하고, DB에서도 삭제
			if (savedFileName  != null) {
				String fullPath = uploadPath + "/" + savedFileName;
				boolean result = FileService.deleteFile(fullPath);
				log.info("파일 삭제 여부: {}", result); // true이면 삭제 완료
			}

			boardRepository.deleteById(boardNum);
		}

	}
	
	/**
	 * DB에 수정 처리
	 * 이전에 첨부 파일이 없는 경우: 파일이 첨부되었을 경우, 저장 작업
	 * 이전 첨부 파일이 있는 상태에서 다른 파일을 첨부했을 경우: 삭제 후 다른 파일로 수정하는 작업
	 * @param board
	 * @return
	 */
	@Transactional
	public void updateBoard(BoardDTO board) {
		MultipartFile uploadFile = board.getUploadFile();

		String originalFileName = null; // 새로운 파일이 첨부되었을 때
		String savedFileName = null; 	// 새로운 파일이 첨부되었을 때
		String oldSavedFileName = null; // 기존에 업로드된 파일이 있을 경우 그 파일명

		// 1단계: 수정하면서 첨부 파일을 송신할 경우
		if (!uploadFile.isEmpty()) {
			originalFileName = uploadFile.getOriginalFilename();
			savedFileName = FileService.saveFile(uploadFile, uploadPath);
		}

		// 2단계: 수정하려는 데이터가 있는지 조회
		Optional<BoardEntity> entity = boardRepository.findById(board.getBoardNum());

		// DB 수정 작업
		if (entity.isPresent()) {
			BoardEntity temp = entity.get();
			oldSavedFileName = temp.getSavedFileName();

			// 기존 파일이 있고 첨부 파일이 있을 경우
			if (oldSavedFileName != null && !uploadFile.isEmpty()) {
				// 예전 파일은 삭제
				String fullPath = uploadPath + "/" + oldSavedFileName;
				FileService.deleteFile(fullPath);

				// 파일명 바꾸기
				temp.setOriginalFileName(originalFileName);
				temp.setSavedFileName(savedFileName);
			}
			
			// 기존 파일은 없고, 수정하면서 첨부된 파일이 있을 경우
			if (oldSavedFileName == null && !uploadFile.isEmpty()) {
				// 파일 삭제는 없이 파일명만 세팅
				temp.setOriginalFileName(originalFileName);
				temp.setSavedFileName(savedFileName);
			}
			
			// 기존 첨부파일이 없을 때, 수정하면서 파일 첨부도 안하면 null값 저장
			temp.setBoardTitle(board.getBoardTitle());
			temp.setBoardContent(board.getBoardContent());
		}
		
	}

	/**
	 * 조회 수 증가
	 * @param boardNum
	 */
	@Transactional
	public void incrementHitcount(Long boardNum) {
		Optional<BoardEntity> entity = boardRepository.findById(boardNum);
		
		if (entity.isPresent()) {
			BoardEntity temp = entity.get(); // hit_count가 들어감
			temp.setHitCount(temp.getHitCount() + 1);
		}
	}

	/**
	 * 게시글은 그대로 두고 파일만 삭제
	 * @param boardNum: 파일이 저장된 게시글 번호
	 */
	@Transactional
	public void deleteFile(Long boardNum) {
		// 데이터 조회
		Optional<BoardEntity> entity = boardRepository.findById(boardNum);

		String oldSavedFileName = null;
		
		// DB 수정 작업
		if (entity.isPresent()) {
			BoardEntity temp = entity.get();
			oldSavedFileName = temp.getSavedFileName();
			
			// 예전 파일은 삭제
			String fullPath = uploadPath + "/" + oldSavedFileName;
			FileService.deleteFile(fullPath);

			// 파일명 null로 바꾸기
			temp.setOriginalFileName(null);
			temp.setSavedFileName(null);
		}
	}
}
