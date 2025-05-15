DROP DATABASE IF EXISTS ssafit;
CREATE DATABASE IF NOT EXISTS ssafit;
USE ssafit;

-- 계정
CREATE TABLE user (
    user_id     VARCHAR(30) PRIMARY KEY,
    password    VARCHAR(50) NOT NULL,
    user_name   VARCHAR(30) NOT NULL,
    email       VARCHAR(30) NOT NULL,
	  is_deleted  boolean DEFAULT false,
		role        VARCHAR(20) NOT NULL DEFAULT "user",
    reg_date    TIMESTAMP DEFAULT CURRENT_TIMESTAMP

);

-- 게시판
CREATE TABLE board (
    board_id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id     VARCHAR(30),
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
    user_id     VARCHAR(30),
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
    user_id     VARCHAR(30),
    done        TINYINT DEFAULT 0, -- 0 : 그냥 좋아요, 1 : 버킷리스트 추가, 2 : 버킷리스트 이행
    done_date   TIMESTAMP,
    FOREIGN KEY (board_id) REFERENCES board(board_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE
);

-- 이메일 인증
CREATE TABLE emailtmp (
    emailtmp_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email       VARCHAR(255),
    token       VARCHAR(255),
    due			TIMESTAMP
);

-- 모임
CREATE TABLE guild (
    guild_id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    guild_name  VARCHAR(255),
    reg_date    TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 모임 구성원
CREATE TABLE crew (
    crew_id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id     VARCHAR(30),
    guild_id    BIGINT,
    roll        VARCHAR(255),
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
