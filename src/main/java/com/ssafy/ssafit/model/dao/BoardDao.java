package com.ssafy.ssafit.model.dao;

import com.ssafy.ssafit.model.dto.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface BoardDao {
    //board 검색
    List<Board> selectBoard();
    //board 상세 조회
    Board selectDetailBoard(String boardId);
    //board 등록
    void insertBoard(Board board);
    //board 수정
    void updateBoard(String boardId);
    //board 삭제
    boolean deleteBoard(String boardId);
}
