DROP DATABASE IF EXISTS ssafit;
CREATE DATABASE IF NOT EXISTS ssafit;
USE ssafit;

-- 계정
CREATE TABLE user (
    user_id     VARCHAR(100) PRIMARY KEY,
    password    VARCHAR(100) NOT NULL,
    user_name   VARCHAR(100) NOT NULL,
    email       VARCHAR(100),
	  is_deleted  boolean DEFAULT false,
		role        VARCHAR(20) NOT NULL DEFAULT "user",
    reg_date    TIMESTAMP DEFAULT CURRENT_TIMESTAMP

);

-- 게시판
CREATE TABLE board (
    board_id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id     VARCHAR(100),
    title       VARCHAR(100),
    content     VARCHAR(2000),
    reg_date    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    view_cnt    INT DEFAULT 0,
    tag         VARCHAR(255),
    is_deleted boolean DEFAULT false,
    FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE SET NULL
);

-- 이미지
CREATE TABLE img (
    img_id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    board_id    BIGINT,
    org_name    VARCHAR(255),
    name        VARCHAR(255),
    FOREIGN KEY (board_id) REFERENCES board(board_id) ON DELETE CASCADE
);

-- 댓글
CREATE TABLE comment (
    comment_id  BIGINT AUTO_INCREMENT PRIMARY KEY,
    board_id    BIGINT,
    user_id     VARCHAR(100),
    content     VARCHAR(3000),
    reg_date    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_date TIMESTAMP,
	  is_deleted  boolean DEFAULT false,
    FOREIGN KEY (board_id) REFERENCES board(board_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE SET NULL
);

-- 버킷리스트
CREATE TABLE bucket (
    bucket_id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    board_id    BIGINT,
    user_id     VARCHAR(100),
    done        TINYINT DEFAULT 0, -- 0 : 그냥 좋아요, 1 : 버킷리스트 추가, 2 : 버킷리스트 이행
    done_date   TIMESTAMP,
    FOREIGN KEY (board_id) REFERENCES board(board_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE
);

-- 이메일 인증
CREATE TABLE email (
    email_id	BIGINT AUTO_INCREMENT PRIMARY KEY,
    address     VARCHAR(255),
    token       VARCHAR(255),
    due			TIMESTAMP,
    verified    TINYINT DEFAULT 0, -- 0 : 노 인정, 1 : 인정 due보다 현재가 작을 경우에만
    INDEX idx_email_address (address)
);

-- 모임
CREATE TABLE guild (
    guild_id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    guild_name  VARCHAR(255) UNIQUE,
    description VARCHAR(1000),
    user_id     VARCHAR(100), -- owner
    reg_date    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    head_count  BIGINT DEFAULT 1, -- 생성자
    FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE SET NULL
);

-- 모임 구성원
CREATE TABLE crew (
    crew_id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id     VARCHAR(100),
    guild_id    BIGINT,
    status      TINYINT, -- 0: 멤버, 1: 초대, 2: 신청
    join_date   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE,
    FOREIGN KEY (guild_id) REFERENCES guild(guild_id) ON DELETE CASCADE
);

-- 팔로우
CREATE TABLE follow (
    follow_id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    follower_id     VARCHAR(100),
    following_id    VARCHAR(100),
    created_at    DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (follower_id) REFERENCES user(user_id) ON DELETE CASCADE,
    FOREIGN KEY (following_id) REFERENCES user(user_id) ON DELETE CASCADE,
    INDEX idx_following_follower (following_id, follower_id)
);

INSERT INTO `user` (user_id, user_name, password, email, role) VALUES
('user01', '김싸피', 'pw1234', 'kim@ssafy.com', 'admin'),
('user02', '이자바', 'java1234', 'lee@java.com', 'user'),
('user03', '박프론트', 'front1234', 'park@web.com', 'user'),
('user04', '문싸피', 'pw1234', 'moon@ssafy.com', 'user'),
('user05', '주싸피', 'pw1234', 'joo@ssafy.com', 'user'),
('user06', '이싸피', 'pw1234', 'lee@ssafy.com', 'user'),
('user07', '변싸피', 'pw1234', 'byun@ssafy.com', 'user');

insert into follow (follower_id, following_id) values
("user01", "user02"),
("user01", "user03"),
("user01", "user04"),
("user01", "user05"),
("user01", "user06"),
("user02", "user01"),
("user03", "user01"),
("user04", "user01"),
("user05", "user01"),
("user07", "user01");

INSERT INTO bucket (board_id, user_id, done, done_date)
VALUES 
(1, 'user01', 0, '2025-05-20 14:30:00'),                              -- 그냥 좋아요
(2, 'user02', 1, '2025-05-20 14:30:00'),             -- 버킷리스트 추가
(3, 'user03', 2, '2025-05-22 10:15:00');    

-- board 테이블 더미 데이터 삽입
INSERT INTO board (user_id, title, content, tag)
VALUES
('user01', '암벽등반 도전기', '처음으로 암벽등반을 해봤는데 생각보다 재밌었어요!', '운동,등산'),
('user02', '스키 처음 탄 날', '처음 타보는 스키! 넘어졌지만 즐거웠던 하루', '운동,겨울스포츠'),
('user03', '버킷리스트: 마라톤 완주', '인생 첫 마라톤 완주! 도전하길 잘했다는 생각이 드네요.', '운동,마라톤');

-- img 더미데이터
insert into img (board_id, org_name, name) values
(1, "img1", "exercise1.jpg"),
(2, "img2", "exercise2.jpg"),
(3, "img3", "exercise3.jpg");
