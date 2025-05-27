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
    reg_date    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status_msg1  VARCHAR(300) DEFAULT "운동은 끝나고 먹는 것까지가 운동이다.",
    status_msg2  VARCHAR(300) DEFAULT "거울은 거짓말 하지 않는다."
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
    title		varchar(255),
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
('user07', '변싸피', 'pw1234', 'byun@ssafy.com', 'user'),
('user08', '홍길동', 'pw1234', 'hong08@example.com', 'user'),
('user09', '임꺽정', 'pw1234', 'lim09@example.com', 'user'),
('user10', '장보고', 'pw1234', 'jang10@example.com', 'user'),
('user11', '유관순', 'pw1234', 'ryu11@example.com', 'user'),
('user12', '안중근', 'pw1234', 'ahn12@example.com', 'user'),
('user13', '윤봉길', 'pw1234', 'yoon13@example.com', 'user'),
('user14', '신사임당', 'pw1234', 'shin14@example.com', 'user'),
('user15', '정약용', 'pw1234', 'jeong15@example.com', 'user'),
('user16', '김구', 'pw1234', 'kim16@example.com', 'user'),
('user17', '이순신', 'pw1234', 'lee17@example.com', 'user'),
('user18', '세종대왕', 'pw1234', 'sejong18@example.com', 'user'),
('user19', '김유신', 'pw1234', 'kim19@example.com', 'user'),
('user20', '최무선', 'pw1234', 'choi20@example.com', 'user');

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
("user07", "user01"),
('user02', 'user03'),
('user03', 'user04'),
('user04', 'user05'),
('user05', 'user06'),
('user06', 'user07'),
('user07', 'user08'),
('user08', 'user09'),
('user09', 'user10'),
('user10', 'user11'),
('user11', 'user12'),
('user12', 'user13'),
('user13', 'user14'),
('user14', 'user15'),
('user15', 'user16'),
('user16', 'user17'),
('user17', 'user18'),
('user18', 'user19'),
('user19', 'user20'),
('user20', 'user01'),
('user08', 'user01'),
('user09', 'user01'),
('user10', 'user01'),
('user11', 'user01'),
('user12', 'user01'),
('user13', 'user01'),
('user14', 'user02'),
('user15', 'user03'),
('user16', 'user04'),
('user17', 'user05'),
('user18', 'user06'),
('user02', 'user05'),
('user02', 'user07'),
('user02', 'user10'),
('user02', 'user11'),
('user03', 'user06'),
('user03', 'user09'),
('user03', 'user13'),
('user03', 'user20'),
('user04', 'user08'),
('user04', 'user12'),
('user04', 'user15'),
('user04', 'user17'),
('user05', 'user09'),
('user05', 'user13'),
('user05', 'user19'),
('user06', 'user02'),
('user06', 'user03'),
('user06', 'user12'),
('user07', 'user04'),
('user07', 'user06'),
('user07', 'user10'),
('user07', 'user14'),
('user08', 'user11'),
('user08', 'user12'),
('user08', 'user16'),
('user08', 'user18'),
('user09', 'user05'),
('user09', 'user08'),
('user09', 'user13'),
('user10', 'user02'),
('user10', 'user03'),
('user10', 'user06'),
('user10', 'user16'),
('user10', 'user17'),
('user11', 'user01'),
('user11', 'user04'),
('user11', 'user09'),
('user11', 'user18'),
('user12', 'user07'),
('user12', 'user11'),
('user12', 'user14'),
('user13', 'user02'),
('user13', 'user05'),
('user14', 'user03'),
('user14', 'user04'),
('user15', 'user06'),
('user15', 'user07'),
('user15', 'user12'),
('user16', 'user08'),
('user17', 'user14'),
('user18', 'user10');

-- board 테이블 더미 데이터 삽입
INSERT INTO board (user_id, title, content, tag)
VALUES
('user01', '암벽등반 도전기', '처음으로 암벽등반을 해봤는데 생각보다 재밌었어요!', '운동,등산'),
('user01', '기구를 이용한 필라테스', '유연성을 위해 필라테스를 해요', '운동,유연성,필라테스'),
('user01', '크로스핏', '크로스핏은 나를 강하게 만들어주지', '운동,크로스핏'),
('user02', '자연에서 요가', '시원한 공기를 마시며 요가를 하면서 화를 식혀요', '운동,명상,요가'),
('user03', '자전거로 국토대장정', '국토대장정을 사이클로! 하체 근육과 지구력을 키울 수 있지', '운동,사이클,지구력'),
('user04', '최상급 스키', '이번 겨울엔 스키를 타봐요! 비발디 파크에서', '스키,겨울'),
('user02', '산책의 매력', '가벼운 산책이지만 마음의 여유를 느낄 수 있어요', '운동,산책,힐링'),
('user03', '수영은 최고의 유산소', '전신을 쓰는 수영으로 체력을 키워요', '운동,수영,유산소'),
('user04', '등산의 참맛', '등산은 땀 흘린 만큼의 보람을 주죠', '운동,등산,유산소'),
('user05', '야구장에서의 짜릿한 순간', '야구는 보는 것도 직접 하는 것도 재밌어요', '야구,스포츠,취미'),
('user06', '풋살 경기 후기', '친구들과 함께한 풋살 경기! 팀워크 최고', '운동,축구,풋살'),
('user07', '서핑은 인생 스포츠', '파도를 타며 느끼는 자유! 서핑 추천해요', '운동,서핑,바다'),
('user08', '테니스 레슨 3일차', '라켓 스윙이 점점 익숙해져요!', '운동,테니스,레슨'),
('user09', '헬스장 루틴 공유', '상체/하체 분할 루틴으로 근성장을 노려요', '운동,헬스,웨이트'),
('user10', '요가로 하루를 시작해요', '하루의 시작을 요가로 여유 있게!', '운동,요가,마인드풀니스'),
('user11', '줄넘기로 다이어트 성공', '매일 1000개씩 줄넘기했더니 체중 감량했어요!', '운동,줄넘기,다이어트'),
('user12', '배드민턴 한 판 어때요?', '가볍게 치는 듯하면서도 격렬한 배드민턴', '운동,배드민턴,레저'),
('user13', '클라이밍 체험', '암벽등반보다 색다른 클라이밍 재미', '운동,클라이밍,실내운동'),
('user14', '주짓수 첫 수업', '몸도 쓰고 머리도 쓰는 종합 격투기 스포츠!', '운동,주짓수,무술'),
('user15', '조깅으로 스트레스 해소', '조깅은 생각 정리에 딱 좋아요', '운동,조깅,스트레스'),
('user16', '농구는 내 삶의 활력소', '슛이 들어가는 쾌감은 말로 설명할 수 없죠', '운동,농구,팀플레이'),
('user17', '볼링 모임 후기', '오랜만에 스트라이크! 볼링장 분위기 최고', '운동,볼링,친목'),
('user18', '스케이트보드 입문', '균형 잡는 게 어렵지만 너무 재밌어요', '운동,보드,스포츠'),
('user19', '스쿼시로 땀 뻘뻘', '작은 공간에서 빠르게 움직이는 스쿼시!', '운동,스쿼시,순발력'),
('user20', '승마 체험기', '말과 함께 달리며 색다른 경험 했어요', '운동,승마,힐링'),
('user01', '복싱으로 다이어트', '스트레스 해소와 운동을 동시에!', '운동,복싱,다이어트'),
('user02', '스노보드 첫 도전', '스키랑은 또 다른 매력의 스노보드', '운동,스노보드,겨울'),
('user03', '줌바댄스로 신나는 하루', '음악에 맞춰 춤추다 보면 시간 가는 줄 몰라요', '운동,줌바,댄스'),
('user04', '패들보드 타봤어요', '물 위에서의 밸런스를 기르는 패들보드', '운동,보드,밸런스'),
('user05', '카약 타며 자연을 만끽', '호수 위에서 즐기는 카약 체험', '운동,카약,자연'),
('user06', '계단 오르기 루틴', '간단하지만 효과 좋은 유산소 운동!', '운동,계단,유산소'),
('user07', '줄넘기 챌린지', '일주일에 7000개 도전 완료!', '운동,챌린지,줄넘기'),
('user08', '스쿼트 100개 챌린지', '다리가 후들거리지만 성취감 최고', '운동,하체,스쿼트'),
('user09', '버피테스트 도전', '진짜 힘든 전신운동이지만 효과는 확실!', '운동,버피,전신운동'),
('user10', '홈트로 체지방 감량 성공', '시간 없을 땐 집에서 홈트로 해결!', '운동,홈트,다이어트'),
('user11', '태권도 품새 연습', '정신 집중에 최고! 전통 무도 태권도', '운동,태권도,무술');

INSERT INTO bucket (board_id, user_id, done, done_date)
VALUES 
(1, 'user01', 0, null),
(2, 'user01', 0, null),
(4, 'user02', 0, null), 
(5, 'user03', 2, '2025-05-22 10:15:00');    

-- img 더미데이터
insert into img (board_id, org_name, name) values
(1, "img1", "exercise1.jpg"),
(2, "img2", "exercise2.jpg"),
(3, "img3", "exercise3.jpg"),
(4, "img4", "exercise4.jpg"),
(5, "img5", "exercise5.jpg"),
(6, 'img6', 'exercise6.jpg'),
(7,  'img7',  'walking.jpg'),
(8,  'img8',  'swimming.jpg'),
(9,  'img9',  'hiking.jpg'),
(10, 'img10', 'baseball.jpg'),
(11, 'img11', 'futsal.jpg'),
(12, 'img12', 'surfing.jpg'),
(13, 'img13', 'tennis.jpg'),
(14, 'img14', 'weight.jpg'),
(15, 'img15', 'yoga.jpg'),
(16, 'img16', 'jumprope.jpg'),
(17, 'img17', 'badminton.jpg'),
(18, 'img18', 'climbing.jpg'),
(19, 'img19', 'jiujitsu.jpg'),
(20, 'img20', 'jogging.jpg'),
(21, 'img21', 'basketball.jpg'),
(22, 'img22', 'bowling.jpg'),
(23, 'img23', 'skateboard.jpg'),
(24, 'img24', 'squash.jpg'),
(25, 'img25', 'horseback.jpg'),
(26, 'img26', 'boxing.jpg'),
(27, 'img27', 'snowboard.jpg'),
(28, 'img28', 'zumba.jpg'),
(29, 'img29', 'paddleboard.jpg'),
(30, 'img30', 'kayak.jpg'),
(31, 'img31', 'stairs.jpg'),
(32, 'img32', 'jumprope2.jpg'),
(33, 'img33', 'squat.jpg'),
(34, 'img34', 'burpee.jpg'),
(35, 'img35', 'homeworkout.jpg'),
(36, 'img36', 'taekwondo.jpg');

INSERT INTO guild (guild_name, description, user_id, head_count)
VALUES
('암벽등반 챌린저스', '암벽등반을 함께 즐기는 모임입니다.', 'user01', 3),
('요가 힐러즈', '요가와 명상을 통한 마음 치유 모임', 'user02', 2),
('사이클러즈', '국토대장정과 장거리 라이딩을 즐기는 사이클 동호회', 'user03', 4),
('겨울 스포츠 동호회', '스키, 스노보드 애호가들의 모임입니다.', 'user04', 3),
('SSAFIT 스터디', '운동 기록 및 도전을 공유하는 SSAFIT 공식 모임', 'user05', 5);

INSERT INTO crew (user_id, guild_id, status)
VALUES
-- guild 1: 암벽등반 챌린저스
('user01', 1, 0), -- owner
('user18', 1, 0),
('user13', 1, 0),

-- guild 2: 요가 힐러즈
('user02', 2, 0), -- owner
('user10', 2, 0),

-- guild 3: 사이클러즈
('user03', 3, 0), -- owner
('user05', 3, 0),
('user08', 3, 0),
('user12', 3, 0),

-- guild 4: 겨울 스포츠 동호회
('user04', 4, 0), -- owner
('user06', 4, 0),
('user20', 4, 0),

-- guild 5: SSAFIT 스터디
('user05', 5, 0), -- owner
('user01', 5, 0),
('user02', 5, 0),
('user03', 5, 0),
('user04', 5, 0);