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
    title       VARCHAR(50),
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
    status      TINYINT, -- 0: 신청, 1: 초대, 2: 멤버
    FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE,
    FOREIGN KEY (guild_id) REFERENCES guild(guild_id) ON DELETE CASCADE
);

-- 팔로우
CREATE TABLE follow (
    follow_id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    follower_id   VARCHAR(30),
    following_id  VARCHAR(30),
    created_at    DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (follower_id) REFERENCES user(user_id) ON DELETE CASCADE,
    FOREIGN KEY (following_id) REFERENCES user(user_id) ON DELETE CASCADE,
    INDEX idx_following_follower (following_id, follower_id)
);

INSERT INTO `user` (user_id, user_name, password, email, role) VALUES
('user01', '김싸피', 'pw1234', 'kim@ssafy.com', 'admin'),
('user02', '이자바', 'java1234', 'lee@java.com', 'user'),
('user03', '박프론트', 'front1234', 'park@web.com', 'user');

insert into follow (follower_id, following_id) values
("user01", "user02"),
("user01", "user03"),
("user02", "user01"),
("user03", "user02");