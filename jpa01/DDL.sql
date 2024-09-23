DROP DATABASE jpatest;
CREATE DATABASE jpatest CHARACTER SET utf8mb4;

-- 사용자 계정 생성 (jpauser)
DROP USER 'jpauser'@'localhost';
CREATE USER 'jpauser'@'localhost' identified BY 'jpauser';

DROP USER 'jpauser'@'%';
CREATE USER 'jpauser'@'%' identified BY 'jpauser';

-- 권한 부여
GRANT ALL PRIVILEGES ON jpatest.* TO 'jpauser'@'localhost';
GRANT ALL PRIVILEGES ON jpatest.* TO 'jpauser'@'%';

-- 샘플 테이블 생성 (Java jpa01 프로젝트와 연동하는 테이블의 생성)
DROP TABLE jpatest.myuser;

CREATE TABLE jpatest.myuser
(
   email   varchar(50) PRIMARY KEY
   , name  varchar(50) NOT NULL
   , join_date datetime DEFAULT current_timestamp
);

SELECT * FROM jpatest.myuser;