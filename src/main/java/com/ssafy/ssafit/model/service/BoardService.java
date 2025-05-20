package com.ssafy.ssafit.model.service;

import com.ssafy.ssafit.model.dto.Board;
import com.ssafy.ssafit.security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

public interface BoardService {
    //board 키워드로 제목/내용 검색 조회
    List<Board> searchBoard(String keyword);
    //board 전체 조회, 마이페이지에 전체 게시글 로드하는데 사용
    List<Board> getAllBoards();
    //board 상세 조회, 댓글,좋아요 달 수 있는 페이지에 로드 할 것
    Board getBoardByBoardId(long boardId);
    //board 등록
    boolean createBoard(Board board);
    //board 수정
    boolean modifyBoard(@AuthenticationPrincipal CustomUserDetails customUserDetails, Board board);
    //board 삭제
    boolean removeBoard(@AuthenticationPrincipal CustomUserDetails customUserDetails, long boardId);
}
