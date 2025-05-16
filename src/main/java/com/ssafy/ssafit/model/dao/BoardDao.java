package com.ssafy.ssafit.model.dao;

import com.ssafy.ssafit.model.dto.Board;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BoardDao {
    //board 검색 조회
    List<Board> searchByKeyword(@Param("keyword") String keyword);
    //board 전체 조회
    List<Board> selectAll();
    //board 상세 조회
    Board selectByBoardId(int boardId);
    //board 등록
    void insertBoard(Board board);
    //board 수정
    void updateBoard(int boardId);
    //board 삭제
    boolean deleteBoard(int boardId);
}
