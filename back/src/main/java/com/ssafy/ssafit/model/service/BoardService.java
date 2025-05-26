package com.ssafy.ssafit.model.service;

import com.ssafy.ssafit.model.dto.Board;
import com.ssafy.ssafit.security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BoardService {
    //board 키워드로 제목/내용 검색 조회
    List<Board> searchBoard(String keyword);
    //board 전체 조회, 마이페이지에 전체 게시글 로드하는데 사용
    List<Board> getAllBoards();
    //로그인 한 유저가 쓴 보드 조회
    List<Board> getBoardByUserId(String userId);
    //board 상세 조회, 댓글,좋아요 달 수 있는 페이지에 로드 할 것
    Board getBoardByBoardId(long boardId);
    //title 조회
    String getTitleByBoardId(long boardId);
    //tag 조회
    String getTagByBoardId(long boardId);
    //board 등록
    Long createBoard(String title, String tag, String content, MultipartFile image, String userId);
    //board 수정
    void updateBoard(Long boardId, String userId, String title, String content, String tag, MultipartFile image);
    //board 삭제
    void removeBoard(String userId, long boardId);
}
