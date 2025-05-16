package com.ssafy.ssafit.model.service;

import com.ssafy.ssafit.model.dto.Board;

import java.util.List;

public interface BoardService {
    //board 검색 조회
    List<Board> searchBoard(String keyword);
    //board 전체 조회
    List<Board> getAllBoards();
    //board 상세 조회
    Board getBoardByBoardId(int boardId);
    //board 등록
    void createBoard(Board board);
    //board 수정
    void modifyBoard(int boardId);
    //board 삭제
    boolean removeBoard(int boardId);
}
