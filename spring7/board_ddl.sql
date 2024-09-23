-- 8월 26일 게시판 관련 테이블 생성
-- 회원 전용 게시판
USE dima;

-- 1) 회원 테이블
DROP TABLE dima.boarduser;
CREATE TABLE dima.boarduser
(
	user_id 	varchar(50)
	, user_name varchar(50) 	NOT NULL
	, user_pwd 	varchar(50) 	NOT NULL
	, email 	varchar(100) 	NOT NULL
	, roles 	varchar(20) 	DEFAULT 'ROLE_USER' -- 'ROLE_USER', 'ROLE_ADMIN'
	, enabled 	char(1) 		DEFAULT '1' -- 사용자의 활성화 상태 여부를 저장, 1 = 활성화, 0 = 비활성화
		, CONSTRAINT boarduser_id PRIMARY KEY(user_id)
		, CONSTRAINT boarduser_roles check(roles IN ('ROLE_USER', 'ROLE_ADMIN'))
		, CONSTRAINT boarduser_enabled check(enabled IN ('1', '0'))
);

-- 2) 게시판 테이블
DROP TABLE dima.board;
CREATE TABLE dima.board
(
	board_num int AUTO_INCREMENT
	, board_writer varchar(50) NOT NULL
	, board_title varchar(200) DEFAULT 'untitled'
	, board_content varchar(3000)
	, hit_count int DEFAULT 0
	, create_date datetime DEFAULT current_timestamp
	, update_date datetime DEFAULT current_timestamp
		, CONSTRAINT board_boardnum PRIMARY KEY(board_num)
);

-- 첨부파일을 위한 컬럼 추가
ALTER TABLE dima.board ADD original_file_name varchar(500); -- 파일의 이름 저장
ALTER TABLE dima.board  ADD saved_file_name varchar(500); -- 변형된 파일명 저장
-- 예시) 이력서.hwp -> 이력서.dkai3fkdy4558.hwp 같은 형태로 저장됨

-- 필요하지 않은 컬럼 삭제 (필요하지 않음)
-- ALTER TABLE dima.board DROP COLUMN original_file_name varchar(500);
-- ALTER TABLE dima.board  DROP COLUMN saved_file_name varchar(500);

COMMIT;
SELECT * FROM dima.board b ;
DELETE FROM dima.board;

-- 3) 댓글 테이블 (게시글과 1:다 관계 형성)
DROP TABLE dima.reply;
CREATE TABLE dima.reply
(
	reply_num int AUTO_INCREMENT
	, board_num int
	, reply_writer varchar(50) NOT NULL
	, reply_text varchar(3000) NOT NULL
	, create_date datetime DEFAULT current_timestamp
		, CONSTRAINT reply_replynum PRIMARY KEY(reply_num)
		, CONSTRAINT reply_boardnum FOREIGN KEY(board_num) REFERENCES dima.board(board_num) ON DELETE CASCADE
);

UPDATE FROM dima.board
SET 
	hit_count = hit_count + 1
WHERE
	board_num = 7
	

SELECT * FROM dima.board;

-- 제목을 이용한 조회
SELECT * FROM dima.board b WHERE board_title LIKE '%는%';

-- 이름을 이용한 조회
SELECT * FROM dima.board WHERE board_writer LIKE '%수%';

-- 작성자 이용
 SELECT * FROM dima.board b WHERE board_writer LIKE '%수%'; ==> findByBoardWriterContains('수')