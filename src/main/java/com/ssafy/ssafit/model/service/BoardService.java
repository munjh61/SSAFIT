package com.ssafy.ssafit.model.service;

import com.ssafy.ssafit.model.dto.Board;

import java.util.List;

public interface BoardService {
    //board 검색
    List<Board> select(Board board);
    //board 상세 조회
    Board selectDetail(String boardId);
    //board 등록
    void insert(Board board);
    //board 수정
    void update(String boardId);
    //board 삭제
    boolean delete(String boardId);
}
