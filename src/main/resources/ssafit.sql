DROP DATABASE IF EXISTS ssafit;
CREATE DATABASE ssafit DEFAULT CHARACTER SET utf8mb4;

USE ssafit;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
	user_id VARCHAR(50) NOT NULL,
    user_name VARCHAR(20) NOT NULL,
    password VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
	role VARCHAR(20) NOT NULL,
    reg_date TIMESTAMP DEFAULT now(),
    is_deleted boolean DEFAULT false,
    PRIMARY KEY(user_id)
)ENGINE=InnoDB;

DROP TABLE IF EXISTS `video`;
CREATE TABLE IF NOT EXISTS `video` (
  `video_id` INT AUTO_INCREMENT,
  `channel_name` varchar(40) NOT NULL,
  `url` varchar(50) NOT NULL,
  `title` varchar(50) NOT NULL,
  `view_cnt` INT DEFAULT 0,
  `reg_date` TIMESTAMP DEFAULT now(),
  `image` VARCHAR(50),
  `part` varchar(50) NOT NULL,
  `user_id` VARCHAR(50) NULL,
  is_deleted boolean DEFAULT false,
  CONSTRAINT `user_fk` FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`)
  -- 부모의 값이 바뀌면 얘를 null로 바꿔버리겠다.
  ON UPDATE SET NULL
  -- 부모의 값이 삭제되면, 얘도 삭제시키겠다.
  ON DELETE CASCADE,
  PRIMARY KEY (`video_id`)
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `review`;
CREATE TABLE `review` (
  review_id INT AUTO_INCREMENT,
  video_id INT,
  user_id VARCHAR(50),
  content VARCHAR(1000),
  reg_date TIMESTAMP DEFAULT now(),
  update_date TIMESTAMP,
  is_deleted boolean DEFAULT false,
  PRIMARY KEY (review_id),
  CONSTRAINT `review_user_fk` FOREIGN KEY (`video_id`) REFERENCES `video`(`video_id`) ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT `review_video_fk` FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE = InnoDB;

INSERT INTO `user` (user_id, user_name, password, email, role) VALUES
('user01', '김싸피', 'pw1234', 'kim@ssafy.com', 'admin'),
('user02', '이자바', 'java1234', 'lee@java.com', 'user'),
('user03', '박프론트', 'front1234', 'park@web.com', 'user');

INSERT INTO `video` 
(channel_name, url, title, view_cnt, image, part, user_id) VALUES
('SSAFY튜브', 'https://youtu.be/ssafy1', 'Spring 입문 강좌', 1500, 'spring.png', 'Spring', 'user01'),
('CodeTalk', 'https://youtu.be/codetalk1', 'MyBatis 실전 예제', 2300, 'mybatis.png', 'MyBatis', 'user02'),
('TechBro', 'https://youtu.be/techbro1', 'Java 정복하기', 5000, 'java.png', 'Java', 'user02'),
('프론트마스터', 'https://youtu.be/front1', 'HTML/CSS 마스터', 1200, 'htmlcss.png', 'Frontend', 'user03'),
('AlgoKing', 'https://youtu.be/algoking1', '백준 DP 강의', 3100, 'dp.png', 'Algorithm', 'user01');

INSERT INTO `review` (video_id, user_id, content) VALUES
(1, 'user02', '정말 유익한 Spring 강좌입니다!'),
(1, 'user03', '기초부터 설명이 잘 되어 있어요.'),
(2, 'user01', 'MyBatis가 이렇게 쉬운 줄 몰랐어요.'),
(3, 'user03', '중급 개발자에게 딱 맞는 강의입니다.'),
(4, 'user01', '프론트엔드 입문자에게 추천합니다.'),
(5, 'user02', 'DP 설명이 이해 잘 됐어요!');


commit;