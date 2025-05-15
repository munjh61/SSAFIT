package com.ssafy.ssafit.model.service;

import com.ssafy.ssafit.model.dto.Board;

import java.util.List;

public interface BoardService {
    //board 검색
    public List<Board> select(Board board);
    //board 상세 조회
    public Board selectDetail(String boardId);
    //board 등록
    public void insert(Board board);
    //board 수정
    public void update(String boardId);
    //board 삭제
    public boolean delete(String boardId);
}
